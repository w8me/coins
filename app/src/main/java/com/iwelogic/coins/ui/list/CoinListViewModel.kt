package com.iwelogic.coins.ui.list

import android.util.Log
import androidx.databinding.ObservableField
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

    val refreshing: ObservableField<Boolean> = ObservableField()

    fun load() {
        refreshing.set(true)
        Log.w("myLog", " LOAD");
        viewModelScope.launch {
            runCatching {
                val queries = HashMap<String, Any>()
                queries["order"] = "market_cap_desc"
                queries["vs_currency"] = "usd"
                val postsRequest = ApiModule.api.getCoins(queries)
                mCoins.value = postsRequest
                refreshing.set(false)
            }.onFailure {
                it.printStackTrace()
                refreshing.set(false)
            }
        }
    }
}