package com.iwelogic.crypto_coins.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class IntDeserializer : JsonDeserializer<Int> {
	override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
		try {
			return json?.asInt ?: 0
		} catch (e: ClassCastException) {
			return json?.asString?.toInt() ?: 0
		}
	}
}

class DoubleDeserializer : JsonDeserializer<Double> {
	override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
		try {
			return json?.asDouble ?: 0.0
		} catch (e: ClassCastException) {
			return json?.asString?.toDouble() ?: 0.0
		}
	}
}

class DateDeserializer : JsonDeserializer<Date> {
	override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
		try {
			val timestamp = json?.asString?.toLong()?.times(1000) ?: 0
			return Date(timestamp)
		} catch (e: NumberFormatException) {
			val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
			dateFormat.timeZone = TimeZone.getTimeZone("GMT")
			return dateFormat.parse(json?.asString)
		}
	}
}
