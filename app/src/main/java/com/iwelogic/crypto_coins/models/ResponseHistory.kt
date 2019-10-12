package com.iwelogic.crypto_coins.models

import com.cfin.cryptofin.entity.HistoryEntity
import com.google.gson.annotations.SerializedName

class ResponseHistory {

    @SerializedName("Data") val data: MutableList<HistoryEntity>? = null
}