package com.iwelogic.coins.data

import android.database.Observable
import com.iwelogic.coins.models.Coin
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.HashMap

interface Api {

    @GET("coins/markets")
    suspend fun getCoins(@QueryMap queries: HashMap<String, Any>): MutableList<Coin>

    @GET("coins/{id}")
    fun getDetails(@Path("id") id: String, @QueryMap queries: HashMap<String, Any>): Observable<Any>
}