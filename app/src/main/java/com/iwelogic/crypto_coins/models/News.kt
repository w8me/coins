package com.iwelogic.crypto_coins.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class News() : Parcelable {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("guid")
    @Expose
    var guid: String? = null
    @SerializedName("published_on")
    @Expose
    var publishedOn: Long? = null
    @SerializedName("imageurl")
    @Expose
    var imageurl: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("source")
    @Expose
    var source: String? = null
    @SerializedName("body")
    @Expose
    var body: String? = null
    @SerializedName("tags")
    @Expose
    var tags: String? = null
    @SerializedName("categories")
    @Expose
    var categories: String? = null
    @SerializedName("upvotes")
    @Expose
    var upvotes: String? = null
    @SerializedName("downvotes")
    @Expose
    var downvotes: String? = null
    @SerializedName("lang")
    @Expose
    var lang: String? = null
    @SerializedName("source_info")
    @Expose
    var sourceInfo: SourceInfo? = null
    var isAd: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        guid = parcel.readString()
        publishedOn = parcel.readValue(Long::class.java.classLoader) as? Long
        imageurl = parcel.readString()
        title = parcel.readString()
        url = parcel.readString()
        source = parcel.readString()
        body = parcel.readString()
        tags = parcel.readString()
        categories = parcel.readString()
        upvotes = parcel.readString()
        downvotes = parcel.readString()
        lang = parcel.readString()
        isAd = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(guid)
        parcel.writeValue(publishedOn)
        parcel.writeString(imageurl)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(source)
        parcel.writeString(body)
        parcel.writeString(tags)
        parcel.writeString(categories)
        parcel.writeString(upvotes)
        parcel.writeString(downvotes)
        parcel.writeString(lang)
        parcel.writeByte(if (isAd) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }

}