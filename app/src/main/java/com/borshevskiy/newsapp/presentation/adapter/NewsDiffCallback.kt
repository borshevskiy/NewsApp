package com.borshevskiy.newsapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.borshevskiy.newsapp.domain.News

object NewsDiffCallback: DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
}