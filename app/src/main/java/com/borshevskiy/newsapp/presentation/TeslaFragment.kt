package com.borshevskiy.newsapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.borshevskiy.newsapp.databinding.FragmentTeslaBinding
import com.borshevskiy.newsapp.domain.NetworkResult
import com.borshevskiy.newsapp.presentation.adapter.NewsListAdapter
import com.borshevskiy.newsapp.util.NetworkListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TeslaFragment : Fragment() {

    private var _binding: FragmentTeslaBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { NewsListAdapter(requireContext()) }
    private lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentTeslaBinding.inflate(inflater, container, false)
        setupRecyclerView()
        checkOnlineAndGetApiData()
        return binding.root
    }

    private fun checkOnlineAndGetApiData() {
        mainViewModel.readBackOnline.observe(viewLifecycleOwner) {
            mainViewModel.backOnline = it
        }
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                mainViewModel.networkStatus = status
                mainViewModel.showNetworkStatus()
                requestApiData()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvNewsList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        showShimmerEffect()
    }

    private fun hideShimmerEffect() {
        with(binding) {
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            rvNewsList.visibility = View.VISIBLE
        }
    }

    private fun showShimmerEffect() {
        with(binding) {
            shimmer.startShimmer()
            shimmer.visibility = View.VISIBLE
            rvNewsList.visibility = View.GONE
        }
    }

    private fun requestApiData() {
        mainViewModel.getTeslaNews()
        mainViewModel.teslaNewsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    with(binding) {
                        errorImageView.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                    }
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.submitList(it)
                    }
                }
                is NetworkResult.Error -> {
                    with(binding) {
                        errorImageView.visibility = View.VISIBLE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = response.message.toString()
                    }
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}