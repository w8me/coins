package com.iwelogic.crypto_coins.ui.news

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.crypto_coins.App
import com.iwelogic.crypto_coins.data.ApiModule
import com.iwelogic.crypto_coins.models.News
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {

    val mNewsList: ObservableList<News> = ObservableArrayList<News>()
    val error: ObservableField<String> = ObservableField("")
    val refreshing: ObservableField<Boolean> = ObservableField(false)
    lateinit var navigation: NewsListNavigation

    fun load() {
        refreshing.set(true)
        viewModelScope.launch {
            runCatching {
                mNewsList.clear()
                error.set("")
                val queries = HashMap<String, Any>()
                queries["lang"] = "EN"
                queries["api_key"] = "359e235f0e51a677c2f6c41a80a7b4de4e334e52c4c0ecfcd9b9c899f5af8939"
                val result = ApiModule.apiHistory.getNews(queries)
                val listNews = result.data
                listNews?.let {
                    if (System.currentTimeMillis() > App.timeX) {
                        for (i in 0 until it.size) {
                            if (i == 5 || i == 30 || i == 55) {
                                val news = News()
                                news.isAd = true
                                listNews.add(i, news)
                            }
                        }
                    }

                    mNewsList.addAll(it)
                }

                refreshing.set(false)
            }.onFailure {
                error.set("Ошибка")
                refreshing.set(false)
                it.printStackTrace()
            }
        }
    }

    val listener: ((News, ImageView, TextView, TextView) -> Unit) = { data, image, title, body ->
        navigation.openDetails(data, image, title, body)
    }

}