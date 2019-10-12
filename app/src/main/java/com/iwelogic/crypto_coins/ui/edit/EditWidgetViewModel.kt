package com.iwelogic.crypto_coins.ui.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.crypto_coins.data.ApiModule
import com.iwelogic.crypto_coins.data.DataBase
import com.iwelogic.crypto_coins.models.Coin
import com.iwelogic.crypto_coins.models.WidgetConfig
import kotlinx.coroutines.launch

class EditWidgetViewModel : ViewModel() {

    var widgetId = 0

    val config = MutableLiveData<WidgetConfig>().apply {
        value =  WidgetConfig()
    }

    val mCoins = MutableLiveData<MutableList<Coin>>().apply {
        value = ArrayList()
    }

    fun setBackgroundColor(color : Int,  alpha: Int, rgb: Int) {
        config.value?.backgroundAlpha = alpha
        config.value?.backgroundRgb = rgb
        config.value?.background = color
        config.postValue(config.value)
    }

    fun clickSave(){
        DataBase.getInstance().writeObject("widgetConfig$widgetId", config.value!!)
    }

    fun load() {
        viewModelScope.launch {
            runCatching {
                val queries = HashMap<String, Any>()
                queries["order"] = "market_cap_desc"
                queries["vs_currency"] = "usd"
                val coins = ApiModule.api.getCoins(queries)
                config.value!!.coin = coins[0]
                config.postValue(config.value)
                mCoins.value = coins
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun onSelectItem(pos : Int){
        config.value!!.coin = mCoins.value!![pos]
        config.postValue(config.value)
    }

    fun setTextColor(selectedColor: Int) {
        config.value?.textColor = selectedColor
        config.postValue(config.value)
    }
}