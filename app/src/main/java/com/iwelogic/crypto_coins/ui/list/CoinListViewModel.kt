package com.iwelogic.crypto_coins.ui.list

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.crypto_coins.data.ApiModule
import com.iwelogic.crypto_coins.models.Coin
import kotlinx.coroutines.launch

class CoinListViewModel : ViewModel() {

    val mCoins = MutableLiveData<MutableList<Coin>>().apply {
        value = ArrayList()
    }

    val refreshing: ObservableField<Boolean> = ObservableField()

    fun load() {
        refreshing.set(true)
        viewModelScope.launch {
            runCatching {
                val queries = HashMap<String, Any>()
                queries["order"] = "market_cap_desc"
                queries["vs_currency"] = "usd"
                val result = ApiModule.api.getCoins(queries)
                for (i in 0 until result.size) {
                    if (i % 9 == 0){
                        val coin = Coin()
                        coin.isAd = true
                        result.add(i, coin)
                    }
                }
                mCoins.value = result
                refreshing.set(false)
            }.onFailure {
                it.printStackTrace()
                refreshing.set(false)
            }
        }
    }
}