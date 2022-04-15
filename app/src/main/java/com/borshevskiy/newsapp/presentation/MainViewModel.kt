package com.borshevskiy.newsapp.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.*
import com.borshevskiy.newsapp.util.DataStoreRepository
import com.borshevskiy.newsapp.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadAppleNewsUseCase: LoadAppleNewsUseCase,
    private val loadTeslaNewsUseCase: LoadTeslaNewsUseCase,
    private val loadBusinessNewsUseCase: LoadBusinessNewsUseCase,
    private val loadTechCrunchNewsUseCase: LoadTechCrunchNewsUseCase,
    private val loadWallStreetNewsUseCase: LoadWallStreetNewsUseCase,
    private val dataStoreRepository: DataStoreRepository,
    application: Application) : AndroidViewModel(application) {

    var networkStatus = false
    var backOnline = false

    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    private val _appleNewsList = MutableLiveData<NetworkResult<List<News>>>()
    val appleNewsList: LiveData<NetworkResult<List<News>>>
        get() = _appleNewsList

    private val _teslaNewsList = MutableLiveData<NetworkResult<List<News>>>()
    val teslaNewsList: LiveData<NetworkResult<List<News>>>
        get() = _teslaNewsList

    private val _businessNewsList = MutableLiveData<NetworkResult<List<News>>>()
    val businessNewsList: LiveData<NetworkResult<List<News>>>
        get() = _businessNewsList

    private val _techCrunchNewsList = MutableLiveData<NetworkResult<List<News>>>()
    val techCrunchNewsList: LiveData<NetworkResult<List<News>>>
        get() = _techCrunchNewsList

    private val _wallStreetNewsList = MutableLiveData<NetworkResult<List<News>>>()
    val wallStreetNewsList: LiveData<NetworkResult<List<News>>>
        get() = _wallStreetNewsList


    private fun saveBackOnline(backOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }

    fun showNetworkStatus() {
        if(!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.",Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if(networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(),"We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

    private suspend fun getNews(newsList: MutableLiveData<NetworkResult<List<News>>>, useCase: UseCase) {
        newsList.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                newsList.value = useCase()
            } catch (e: Exception) {
                newsList.value = NetworkResult.Error("News not found.")
            }
        } else {
            newsList.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    fun getAppleNews() = viewModelScope.launch {
        getNews(_appleNewsList,loadAppleNewsUseCase)
    }

    fun getTeslaNews() = viewModelScope.launch {
        getNews(_teslaNewsList,loadTeslaNewsUseCase)
    }

    fun getBusinessNews() = viewModelScope.launch {
        getNews(_businessNewsList,loadBusinessNewsUseCase)
    }

    fun getTechCrunchNews() = viewModelScope.launch {
        getNews(_techCrunchNewsList,loadTechCrunchNewsUseCase)
    }

    fun getWallStreetNews() = viewModelScope.launch {
        getNews(_wallStreetNewsList,loadWallStreetNewsUseCase)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}