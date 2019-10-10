package com.iwelogic.coins.data

import com.cfin.cryptofin.entity.HistoryEntity
import com.iwelogic.coins.models.ResponseHistory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.HashMap

interface ApiHistory {

    @GET("data/histoday")
    suspend fun getHistory(@QueryMap queries: HashMap<String, Any>): ResponseHistory
}