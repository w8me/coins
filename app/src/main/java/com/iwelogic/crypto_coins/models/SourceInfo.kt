package com.iwelogic.crypto_coins.models

import com.google.gson.annotations.SerializedName

class SourceInfo {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("lang")
    var lang: String? = null
    @SerializedName("img")
    var img: String? = null
}