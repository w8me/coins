package com.iwelogic.crypto_coins.ui.coins

import com.iwelogic.crypto_coins.models.Coin

interface CoinListNavigator {
    fun openDetails(coin: Coin)
}