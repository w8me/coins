package com.iwelogic.coins.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.HashMap

interface ApiHistory {

    @GET("data/histoday")
    fun getHistory(@QueryMap queries: HashMap<String, Any>): Observable<Any>
}