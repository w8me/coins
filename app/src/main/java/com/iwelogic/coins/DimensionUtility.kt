package com.iwelogic.coins

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.NonNull

object DimensionUtility {

    fun dp2px(@NonNull context: Context, dp: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    }

    fun sp2px(@NonNull context: Context, sp: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics)
    }

    fun px2dp(@NonNull context: Context, px: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return px / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun px2sp(@NonNull context: Context, px: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return px / displayMetrics.scaledDensity
    }
}
