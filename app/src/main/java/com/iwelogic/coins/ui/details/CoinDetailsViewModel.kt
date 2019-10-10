package com.iwelogic.coins.ui.details

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfin.cryptofin.entity.HistoryEntity
import com.iwelogic.coins.data.ApiModule
import com.iwelogic.coins.models.Coin
import kotlinx.coroutines.launch

class CoinDetailsViewModel : ViewModel() {

    val history: ObservableList<HistoryEntity> = ObservableArrayList<HistoryEntity>()
    lateinit var coin: Coin

    fun load() {
        viewModelScope.launch {
            runCatching {
                val queries = HashMap<String, Any>()
                queries["tsym"] = "USD"
                queries["e"] = "CCCAGG"
                queries["allData"] = true
                queries["limit"] = 2000
                queries["fsym"] = coin.symbol!!.toUpperCase()
                queries["aggregate"] = 1
                val postsRequest = ApiModule.apiHistory.getHistory(queries)
                history.clear()
                history.addAll(postsRequest.data!!)
                Log.w("myLog", "HISTORY " + history.size);
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}