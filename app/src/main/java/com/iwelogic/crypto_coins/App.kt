package com.iwelogic.crypto_coins

import android.app.Application
import com.google.android.gms.ads.MobileAds

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        MobileAds.initialize(this)
    }
}