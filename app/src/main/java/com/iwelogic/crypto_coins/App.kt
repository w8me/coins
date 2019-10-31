package com.iwelogic.crypto_coins

import android.app.Application
import com.google.android.gms.ads.MobileAds

class App : Application() {



    companion object {
        lateinit var instance: App
       // val timeX = 1578735938317
        val timeX = 0
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        MobileAds.initialize(this)
    }
}