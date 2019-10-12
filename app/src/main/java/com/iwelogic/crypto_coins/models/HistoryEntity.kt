package com.cfin.cryptofin.entity

import com.google.gson.annotations.SerializedName
import java.util.Date

data class HistoryEntity(
	@SerializedName("time") val time: Date?,
	@SerializedName("open") val open: Double = 0.0,
	@SerializedName("close") val close: Double = 0.0,
	@SerializedName("high") val high: Double = 0.0,
	@SerializedName("low") val low: Double = 0.0,
	@SerializedName("volumefrom") val volumefrom: Double = 0.0,
	@SerializedName("volumeto") val volumeto: Double = 0.0)
