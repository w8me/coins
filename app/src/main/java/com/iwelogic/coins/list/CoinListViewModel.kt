package com.iwelogic.coins.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.coins.data.ApiModule
import com.iwelogic.coins.models.Coin
import kotlinx.coroutines.launch

class CoinListViewModel : ViewModel() {

    val mCoins = MutableLiveData<MutableList<Coin>>().apply {
        value = ArrayList()
    }

    fun load() {
        viewModelScope.launch {
            runCatching {
                val queries = HashMap<String, Any>()
                queries["order"] = "market_cap_desc"
                queries["vs_currency"] = "usd"
                val postsRequest = ApiModule.api.getCoins(queries)
                mCoins.value = postsRequest
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}