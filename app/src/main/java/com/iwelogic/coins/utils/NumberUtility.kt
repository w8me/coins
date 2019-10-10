package com.iwelogic.coins.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class NumberUtility {
    companion object {
        @JvmStatic
        fun formatNumber(price: Double): String {
            val formatter = DecimalFormat("###,###")
            return formatter.format(price)
        }

        @JvmStatic
        fun formatPrice(price: Double?): String {
            val format = when {
                price == null -> return "0"
                price < 1.0 -> "$###.######"
                price < 10.0 -> "$###.###"
                price < 1000.0 -> "$###.##"
                else -> "$###.#"
            }
            val formatter = DecimalFormat(format)
            return formatter.format(price)
        }

        @JvmStatic
        fun formatHighPrice(price: Double): String {
            val formatter = DecimalFormat("$###,###")
            return formatter.format(price)
        }

        @JvmStatic
        fun formatPercent(percent: Double): String {
            val formatter = DecimalFormat("+##.##;-#")
            return formatter.format(percent) + "%"
        }

        @JvmStatic
        fun formatDate(date: Date?): String? {
            val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
            return if (date != null) formatter.format(date) else null
        }

        @JvmStatic
        fun formatDateTime(date: Date?): String? {
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US)
            return if (date != null) formatter.format(date) else null
        }

        @JvmStatic
        fun formatGenesisDate(date: String?): String? {
            return date?.replace('-', '/')
        }
    }
}