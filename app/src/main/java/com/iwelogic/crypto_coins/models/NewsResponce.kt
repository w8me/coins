package com.iwelogic.crypto_coins.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResponce {
    @SerializedName("Type")
    @Expose
    var type: Int? = null
    @SerializedName("Message")
    @Expose
    var message: String? = null
    @SerializedName("Promoted")
    @Expose
    var promoted: List<Any>? = null
    @SerializedName("Data")
    @Expose
    var data: MutableList<News>? = null
    @SerializedName("RateLimit")
    @Expose
    var rateLimit: RateLimit? = null
    @SerializedName("HasWarning")
    @Expose
    var hasWarning: Boolean? = null

}