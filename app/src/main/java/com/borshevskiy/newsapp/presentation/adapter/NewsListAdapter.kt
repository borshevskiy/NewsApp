package com.borshevskiy.newsapp.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.borshevskiy.newsapp.R
import com.borshevskiy.newsapp.databinding.NewsRowLayoutBinding
import com.borshevskiy.newsapp.domain.News
import org.jsoup.Jsoup


class NewsListAdapter(private val context: Context): ListAdapter<News, NewsViewHolder>(NewsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(NewsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        with(holder.binding) {
            with(news) {
                newsTitle.text = title
                newsDescription.text = Jsoup.parse(description).text()
                newsAuthor.text = author
                newsTime.text = "Published At $publishedAt"
                newsImageView.load(urlToImage) { crossfade(600)
                    error(R.drawable.ic_no_image)}
                buttonFull.setOnClickListener {
                    context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
                }
            }
        }
    }
}