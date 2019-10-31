package com.iwelogic.crypto_coins.ui.coins

import android.widget.ImageView
import android.widget.TextView
import com.iwelogic.crypto_coins.models.Coin

interface CoinListNavigator {
    fun openDetails(coin: Coin, name: TextView, image: ImageView)
}