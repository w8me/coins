package com.iwelogic.crypto_coins.data

import com.iwelogic.crypto_coins.models.NewsResponce
import com.iwelogic.crypto_coins.models.ResponseHistory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

interface ApiHistory {

    @GET("data/histoday")
    suspend fun getHistory(@QueryMap queries: HashMap<String, Any>): ResponseHistory

    @GET("data/v2/news/")
    suspend fun getNews(@QueryMap queries: HashMap<String, Any>): NewsResponce
}