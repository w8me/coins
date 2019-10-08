package com.iwelogic.coins.models

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

class Coin() : Parcelable {

    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double? = null

    @SerializedName("symbol")
    val symbol: String? = null

    @SerializedName("image")
    val image: String? = null

    @SerializedName("circulating_supply")
    val circulatingSupply: Double? = null

    @SerializedName("total_volume")
    val totalVolume: Double? = null

    @SerializedName("price_change_24h")
    val priceChange24h: Double? = null

    @SerializedName("last_updated")
    val lastUpdated: String? = null

    @SerializedName("total_supply")
    val totalSupply: Double? = null

    @SerializedName("market_cap_rank")
    val marketCapRank: Int? = null

    @SerializedName("roi")
    val roi: Any? = null

    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double? = null

    @SerializedName("market_cap")
    val marketCap: Long? = null

    @SerializedName("ath")
    val ath: Double? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("high_24h")
    val high24h: Double? = null

    @SerializedName("low_24h")
    val low24h: Double? = null

    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double? = null

    @SerializedName("id")
    val id: String? = null

    @SerializedName("current_price")
    val currentPrice: Double? = null

    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double? = null

    @SerializedName("ath_date")
    val athDate: String? = null

    constructor(parcel: Parcel) : this() {
    }

    @BindingAdapter("image")
    fun loadImage(imageView: ImageView, imageURL: String) {
        Glide.with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .circleCrop()
            )
            .load(imageURL)
            .into(imageView)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }
}