package com.iwelogic.coins.widget

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.iwelogic.coins.R
import com.iwelogic.coins.data.ApiModule
import com.iwelogic.coins.data.DataBase
import com.iwelogic.coins.models.Coin
import com.iwelogic.coins.models.WidgetConfig
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.HashMap

class WidgetService : Service() {
    private val mCompositeDisposable = CompositeDisposable()

    companion object {
        const val REFRESH_WIDGET = "refresh"
        const val CHANNEL = "Reload widget data"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(CHANNEL, CHANNEL, NotificationManager.IMPORTANCE_MIN)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(this, CHANNEL).setContentTitle("").setContentText("").build()
            startForeground(1, notification)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var widgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)
        if (widgetIds == null) {
            widgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(ComponentName(this, CoinWidget::class.java))
        }

        for (widgetId in widgetIds!!) {
            val remoteView = RemoteViews(this.applicationContext.packageName, R.layout.widget_coin)
            remoteView.setViewVisibility(R.id.progressBar, View.VISIBLE)
            AppWidgetManager.getInstance(this).partiallyUpdateAppWidget(widgetId, remoteView)
        }

        GlobalScope.launch {
            runCatching {
                val queries = HashMap<String, Any>()
                queries["order"] = "market_cap_desc"
                queries["vs_currency"] = "usd"
                val coins = ApiModule.api.getCoins(queries)
                for (widgetId in widgetIds) {
                    changeWidget(coins, widgetId)
                }
                stopSelf()
            }.onFailure {
                it.printStackTrace()
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun changeWidget(coins: List<Coin>, widgetId: Int) {

        val remoteView = RemoteViews(this.applicationContext.packageName, R.layout.widget_coin)

        val widgetConfig = DataBase.getInstance().readObject("widgetConfig$widgetId", WidgetConfig::class.java)
        remoteView.setInt(R.id.background, "setAlpha", widgetConfig.backgroundAlpha)
        remoteView.setInt(R.id.background, "setColorFilter", widgetConfig.backgroundRgb)
        remoteView.setTextViewText(R.id.price, "${coins[0].currentPrice}")
        remoteView.setTextViewText(R.id.name, coins[0].name)
        remoteView.setViewVisibility(R.id.progressBar, View.GONE)

        val icon = Glide.with(this@WidgetService.baseContext)
            .asBitmap()
            .load(coins[0].image)
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()
        remoteView.setImageViewBitmap(R.id.icon, icon)

        val refreshWidget = Intent(this.applicationContext, CoinWidget::class.java)
        refreshWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        refreshWidget.action = REFRESH_WIDGET
        refreshWidget.data = Uri.parse(refreshWidget.toUri(Intent.URI_INTENT_SCHEME))
        val pendingRefresh = PendingIntent.getBroadcast(this.applicationContext, 0, refreshWidget, 0)
        remoteView.setOnClickPendingIntent(R.id.main, pendingRefresh)
        AppWidgetManager.getInstance(this).partiallyUpdateAppWidget(widgetId, remoteView)
    }
}