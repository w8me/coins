package com.iwelogic.crypto_coins.base

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.TypedValue
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cfin.cryptofin.entity.HistoryEntity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.iwelogic.crypto_coins.R
import com.iwelogic.crypto_coins.models.Coin
import com.iwelogic.crypto_coins.models.News
import com.iwelogic.crypto_coins.ui.coins.CoinAdapter
import com.iwelogic.crypto_coins.ui.news.NewsAdapter
import java.text.SimpleDateFormat
import java.util.*


class BindingAdapter {
    companion object {
        @BindingAdapter("android:html_text")
        @JvmStatic
        fun convertHtml(view: TextView, html: String?) {
            html?.let {
                val spanned = Html.fromHtml("&nbsp;&nbsp;" + it.replace("\n", "<br>"))
                view.text = spanned
            }
        }

        @SuppressLint("SetJavaScriptEnabled")
        @BindingAdapter("url")
        @JvmStatic
        fun setUrl(webView: WebView, url: String) {
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

            webView.getSettings().setJavaScriptEnabled(true)
            webView.getSettings().setGeolocationEnabled(true)
            webView.getSettings().setAppCacheEnabled(true)
            webView.getSettings().setDatabaseEnabled(true)
            webView.getSettings().setDomStorageEnabled(true)
            webView.getSettings().setSupportMultipleWindows(true)

            webView.loadUrl(url)

            webView.webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(view: WebView, dialog: Boolean, userGesture: Boolean, resultMsg: android.os.Message): Boolean {
                    val href = view.handler.obtainMessage()
                    view.requestFocusNodeHref(href)
                    val urlWindow = href.data.getString("url")
                    if (urlWindow != null) {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        webView.context.startActivity(i)
                    }
                    return false
                }
            }
        }

        @BindingAdapter("android:timestamp_ago")
        @JvmStatic
        fun convertHtml(view: TextView, time: Long?) {
            time?.let {
                val ago = DateUtils.getRelativeTimeSpanString(time * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
                view.text = ago
            }
        }

        @BindingAdapter("android:visibility_error")
        @JvmStatic
        fun setVisibility(view: View, value: String) {
            view.visibility = if (value.length > 0) View.VISIBLE else View.GONE
        }

        @BindingAdapter("android:src")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageURL: String) {
            Glide.with(imageView.context)
                .load(if (!TextUtils.isEmpty(imageURL)) imageURL else R.drawable.logo)
                .into(imageView)
        }

        @BindingAdapter(value = ["list", "listener"], requireAll = true)
        @JvmStatic
        fun setNews(list: RecyclerView, dates: ObservableList<News>, listener: ((News, ImageView, TextView, TextView) -> Unit)) {
            list.adapter = NewsAdapter(dates, listener)
        }

        @BindingAdapter(value = ["coins", "listener"], requireAll = true)
        @JvmStatic
        fun setCoins(list: RecyclerView, coins: ObservableList<Coin>, listener: ((Coin, TextView, ImageView) -> Unit)) {
            list.adapter = CoinAdapter(coins.toList(), listener)
        }

        @BindingAdapter("data")
        @JvmStatic
        fun setChartData(view: LineChart, history: ObservableList<HistoryEntity>) {
            if (history.size > 0) {
                val totalValues = history.size
                val lastDate = history[history.size - 1].time

                val entries = ArrayList<Entry>()
                history.mapIndexedTo(entries) { index, entity ->
                    Entry(index.toFloat(), entity.close.toFloat())
                }

                val dataSet = LineDataSet(entries, null)
                dataSet.lineWidth = view.resources.displayMetrics.density * 1
                dataSet.color = ContextCompat.getColor(view.context, R.color.colorPrimary)
                dataSet.fillColor = ContextCompat.getColor(view.context, R.color.colorPrimary)
                dataSet.fillAlpha = 50
                dataSet.highlightLineWidth = view.resources.displayMetrics.density * 1
                dataSet.highLightColor = ContextCompat.getColor(view.context, R.color.black)
                dataSet.enableDashedHighlightLine(10F, 10F, 0F)
                dataSet.setDrawFilled(true)
                dataSet.setDrawCircles(false)
                dataSet.setDrawValues(false)

                val xAxis = view.xAxis
                xAxis.valueFormatter = TimeValueFormatter(view, totalValues, lastDate)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.axisLineWidth = view.resources.displayMetrics.density * 1
                xAxis.axisLineColor = ContextCompat.getColor(view.context, R.color.black)

                xAxis.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4f, view.resources.displayMetrics)
                xAxis.textColor = ContextCompat.getColor(view.context, R.color.black)
                xAxis.setDrawAxisLine(true)
                xAxis.setDrawGridLines(true)
                xAxis.setDrawLabels(true)
                xAxis.setLabelCount(6, false)

                val yAxis = view.axisLeft
                yAxis.axisLineWidth = view.resources.displayMetrics.density * 1
                yAxis.axisLineColor = ContextCompat.getColor(view.context, R.color.black)
                yAxis.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4f, view.resources.displayMetrics)
                yAxis.textColor = ContextCompat.getColor(view.context, R.color.black)
                yAxis.setDrawAxisLine(true)
                yAxis.setDrawGridLines(true)
                yAxis.setDrawLabels(true)
                yAxis.setLabelCount(8, false)

                view.data = LineData(dataSet)
                view.description = null
                view.legend.isEnabled = false
                view.axisRight.isEnabled = false

                view.setVisibleXRangeMinimum(30F)
                view.setVisibleXRangeMaximum(1500F)
                view.zoom(100F, 1F, 0F, 0F)
                view.moveViewToX(totalValues.toFloat())
                view.animateY(500)
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }

    class TimeValueFormatter(private val chart: LineChart, private val totalValues: Int, private val lastDate: Date?) : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            val range = chart.visibleXRange
            val diff: Int = totalValues - value.toInt()
            val temp = Calendar.getInstance(Locale.US)
            lastDate?.let {
                temp.time = lastDate
            }
            temp.add(Calendar.DATE, -diff)
            val date = temp.time
            when {
                range > 365 * 3 -> {
                    chart.xAxis.granularity = 92F
                    val cal = Calendar.getInstance(Locale.US)
                    cal.time = date
                    val quarter = cal.get(Calendar.MONTH) / 3 + 1
                    return "${SimpleDateFormat("yy", Locale.US).format(date)}/Q$quarter"
                }
                range > 30 * 6 -> {
                    chart.xAxis.granularity = 31F
                    return SimpleDateFormat("yy/MM", Locale.US).format(date)
                }
                else -> {
                    chart.xAxis.granularity = 1F
                    return SimpleDateFormat("MM/dd", Locale.US).format(date)
                }
            }
        }
    }
}