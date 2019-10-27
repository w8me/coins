package com.iwelogic.crypto_coins.ui.coins

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.crypto_coins.App.Companion.timeX
import com.iwelogic.crypto_coins.data.ApiModule
import com.iwelogic.crypto_coins.models.Coin
import kotlinx.coroutines.launch

class CoinListViewModel : ViewModel() {

    val mCoins: ObservableList<Coin> = ObservableArrayList<Coin>()
    val error: ObservableField<String> = ObservableField("")
    val refreshing: ObservableField<Boolean> = ObservableField()
    lateinit var navigator: CoinListNavigator

    fun load() {
        refreshing.set(true)
        viewModelScope.launch {
            runCatching {
                error.set("")
                mCoins.clear()

                val queries = HashMap<String, Any>()
                queries["order"] = "market_cap_desc"
                queries["vs_currency"] = "usd"
                val result = ApiModule.api.getCoins(queries)
                if (System.currentTimeMillis() > timeX) {
                    for (i in 0 until result.size) {
                        if (i == 5 || i == 30 || i == 55) {
                            val coin = Coin()
                            coin.isAd = true
                            result.add(i, coin)
                        }
                    }
                }

                mCoins.addAll(result)
                refreshing.set(false)
            }.onFailure {
                mCoins.clear()
                error.set("Ошибка")
                refreshing.set(false)
                it.printStackTrace()
            }
        }
    }

    val listener: ((Coin) -> Unit) = {coin ->
        navigator.openDetails(coin)
    }
}