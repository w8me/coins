package com.iwelogic.coins.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iwelogic.coins.data.DataBase
import com.iwelogic.coins.models.WidgetConfig

class EditWidgetViewModel : ViewModel() {

    var widgetId = 0

    val config = MutableLiveData<WidgetConfig>().apply {
        value =  WidgetConfig()
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
}