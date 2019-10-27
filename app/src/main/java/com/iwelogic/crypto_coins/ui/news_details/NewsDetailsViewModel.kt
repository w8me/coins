package com.iwelogic.crypto_coins.ui.news_details

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.iwelogic.crypto_coins.models.News

class NewsDetailsViewModel : ViewModel() {
    var data: ObservableField<News> = ObservableField()
    lateinit var navigator: NewsDetailsNavigator
}