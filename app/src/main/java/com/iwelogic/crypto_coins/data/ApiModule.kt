package com.iwelogic.crypto_coins.data

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiModule {

    companion object {
        var api: Api = provideApi()
        var apiHistory: ApiHistory = provideApiHistory()

        private fun provideApi(): Api {
            return provideRetrofitBuilder()
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build().create(Api::class.java)
        }


        private fun provideApiHistory(): ApiHistory {
            return provideRetrofitBuilder()
                .baseUrl("https://min-api.cryptocompare.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build().create(ApiHistory::class.java)
        }

        private fun provideRetrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(provideConverterFactory())
        }


        private fun provideConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create(
                GsonBuilder()
                    .registerTypeAdapter(Int::class.java, IntDeserializer())
                    .registerTypeAdapter(Double::class.java, DoubleDeserializer())
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .serializeNulls()
                    .create()
            )
        }

        private fun provideOkHttpClient(): OkHttpClient {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val builder = OkHttpClient.Builder()
                builder.addInterceptor(logging)
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                //       builder.hostnameVerifier((hostname, session) -> true);

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}