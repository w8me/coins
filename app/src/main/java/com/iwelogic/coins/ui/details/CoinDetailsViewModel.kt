package com.iwelogic.coins.ui.details

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfin.cryptofin.entity.HistoryEntity
import com.iwelogic.coins.data.ApiModule
import com.iwelogic.coins.models.Coin
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class CoinDetailsViewModel : ViewModel() {

    val history: ObservableList<HistoryEntity> = ObservableArrayList<HistoryEntity>()
    var description : ObservableField<String> = ObservableField()
    val refreshing: ObservableField<Boolean> = ObservableField()

    lateinit var coin: Coin

    fun load() {
        refreshing.set(true)
         viewModelScope.launch {
             runCatching {
                 val queries = HashMap<String, Any>()
                 queries["tsym"] = "USD"
                 queries["e"] = "CCCAGG"
                 queries["allData"] = true
                 queries["limit"] = 2000
                 queries["fsym"] = coin.symbol!!.toUpperCase(Locale.getDefault())
                 queries["aggregate"] = 1
                 val postsRequest = ApiModule.apiHistory.getHistory(queries)


                 val queriesDetails = HashMap<String, Any>()
                 queriesDetails["localization"] = false
                 queriesDetails["tickers"] = false
                 queriesDetails["market_data"] = true
                 queriesDetails["community_data"] = false
                 queriesDetails["developer_data"] = false
                 val result = ApiModule.api.getDetails(coin.id, queriesDetails)
                 description.set(result.description.en)

                 history.clear()
                 history.addAll(postsRequest.data!!)
                 refreshing.set(false)
             }.onFailure {
                 it.printStackTrace()
                 refreshing.set(false)
             }
         }
    }
}