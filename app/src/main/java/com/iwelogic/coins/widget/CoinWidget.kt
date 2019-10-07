package com.iwelogic.coins.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.iwelogic.coins.widget.WidgetService.Companion.REFRESH_WIDGET

class CoinWidget : AppWidgetProvider(){
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        val widgetSizes = IntArray(appWidgetIds.size)
        val refresh = Intent(context, WidgetService::class.java)
        refresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds)
        refresh.data = Uri.parse(refresh.toUri(Intent.URI_INTENT_SCHEME))

        for (i in appWidgetIds.indices) {
            widgetSizes[i] = appWidgetManager.getAppWidgetOptions(appWidgetIds[i]).getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        }
        refresh.putExtra("SIZE_WIDGETS", widgetSizes)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(refresh)
        } else {
            context.startService(refresh)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }

    override fun onReceive(context: Context, i: Intent) {
        super.onReceive(context, i)
        if (i.extras != null && i.action != null) {
            val widgetId = i.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                if (i.action == REFRESH_WIDGET) {
                    this.onUpdate(context, AppWidgetManager.getInstance(context), intArrayOf(widgetId))
                }
            }
        }
    }
}