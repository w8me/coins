package com.iwelogic.crypto_coins.data

import com.iwelogic.crypto_coins.models.Coin
import com.iwelogic.crypto_coins.models.CoinDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.HashMap

interface Api {

    @GET("coins/markets")
    suspend fun getCoins(@QueryMap queries: HashMap<String, Any>): MutableList<Coin>

    @GET("coins/{id}")
    suspend fun getDetails(@Path("id") id: String, @QueryMap queries: HashMap<String, Any>): CoinDetails
}