package com.iwelogic.crypto_coins.ui.news

import android.widget.ImageView
import android.widget.TextView
import com.iwelogic.crypto_coins.models.News

interface NewsListNavigation {
    fun openDetails(data: News, imageView: ImageView, title: TextView, body: TextView)
}