package com.iwelogic.crypto_coins.base

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Html
import android.text.TextUtils
import android.text.format.DateUtils
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
import com.iwelogic.crypto_coins.utils.DimensionUtility
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

            webView.webChromeClient = object : WebChromeClient(){
                override fun onCreateWindow(view: WebView, dialog: Boolean, userGesture: Boolean, resultMsg: android.os.Message): Boolean {
                    val href = view.handler.obtainMessage()
                    view.requestFocusNodeHref(href)
                    val urlWindow = href.data.getString("url")
                    if (urlWindow != null){
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
        fun setCoins(list: RecyclerView, coins: ObservableList<Coin>, listener: ((Coin) -> Unit)) {
            list.adapter = CoinAdapter(coins.toList(), listener)
        }

        @BindingAdapter("chartData")
        @JvmStatic
        fun setChartData(lineChart: LineChart, history: ObservableList<HistoryEntity>) {
            if (history.size > 0) {
                setupLineChart(lineChart, history.toList())
                lineChart.visibility = View.VISIBLE
            } else {
                lineChart.visibility = View.GONE
            }
        }

        fun setupLineChart(lineChart: LineChart, history: List<HistoryEntity>) {
            val context = lineChart.context
            val maxVisibleValues = 365 * 5
            val totalValues = history.size
            val lastDate = history[history.size - 1].time

            // dimens
            val lineWidth: Float = DimensionUtility.px2dp(context, context.resources.getDimension(R.dimen.chart_line_width))
            val axisWidth: Float = DimensionUtility.px2dp(context, context.resources.getDimension(R.dimen.chart_axis_width))
            val gridWidth: Float = DimensionUtility.px2dp(context, context.resources.getDimension(R.dimen.chart_grid_width))
            val highlightWidth: Float = DimensionUtility.px2dp(context, context.resources.getDimension(R.dimen.chart_highlight_width))
            val textSize: Float = DimensionUtility.px2dp(context, context.resources.getDimension(R.dimen.chart_text_size))

            // colors
            val lineColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
            val axisColor: Int = ContextCompat.getColor(context, R.color.black)
            val highlightColor: Int = ContextCompat.getColor(context, R.color.black)
            val fillColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
            val textColor: Int = ContextCompat.getColor(context, R.color.black)

            // data
            val entries = ArrayList<Entry>()
            history.mapIndexedTo(entries) { index, entity ->
                Entry(index.toFloat(), entity.close.toFloat())
            }

            // data set style
            val dataSet = LineDataSet(entries, null)
            dataSet.lineWidth = lineWidth
            dataSet.color = lineColor
            dataSet.fillColor = fillColor
            dataSet.fillAlpha = 48
            dataSet.highlightLineWidth = highlightWidth
            dataSet.highLightColor = highlightColor
            dataSet.enableDashedHighlightLine(10F, 10F, 0F)
            dataSet.setDrawFilled(true)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)

            // x axis style
            val xAxis = lineChart.xAxis
            xAxis.valueFormatter = DayAxisValueFormatter(lineChart, totalValues, lastDate)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.labelRotationAngle = -45F
            xAxis.axisLineWidth = axisWidth
            xAxis.axisLineColor = axisColor

            xAxis.textSize = textSize
            xAxis.textColor = textColor
            xAxis.typeface = Typeface.SANS_SERIF
            xAxis.setDrawAxisLine(true)
            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(true)
            xAxis.setLabelCount(8, false)

            // y axis style
            val yAxis = lineChart.axisLeft
            yAxis.axisLineWidth = axisWidth
            yAxis.axisLineColor = axisColor
            yAxis.textSize = textSize
            yAxis.textColor = textColor
            yAxis.typeface = Typeface.SANS_SERIF
            yAxis.setDrawAxisLine(true)
            yAxis.setDrawGridLines(true)
            yAxis.setDrawLabels(true)
            yAxis.setLabelCount(10, false)

            // chart settings
            lineChart.data = LineData(dataSet)
            lineChart.description = null
            lineChart.legend.isEnabled = false
            lineChart.axisRight.isEnabled = false

            // chart viewport
            lineChart.isScaleYEnabled = false
            lineChart.setVisibleXRangeMinimum(30F)
            lineChart.setVisibleXRangeMaximum(maxVisibleValues.toFloat())
            lineChart.zoom(determineZoom(totalValues, maxVisibleValues), 1F, 0F, 0F)
            lineChart.moveViewToX(totalValues.toFloat()) // automatically calls invalidate()
            lineChart.animateY(1000)
        }

        fun determineZoom(totalValues: Int, maxVisibleValues: Int): Float {
            val visiblePoints: Int = if (totalValues > maxVisibleValues) maxVisibleValues else totalValues
            return visiblePoints / 182F
        }
    }


    class DayAxisValueFormatter(private val chart: LineChart, private val totalValues: Int, private val lastDate: Date?) : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            val range = chart.visibleXRange
            val date = determineDate(value, totalValues, lastDate)
            return when {
                range > 365 * 3 -> {
                    chart.xAxis.granularity = 92F
                    formatQuarter(date)
                }
                range > 30 * 6 -> {
                    chart.xAxis.granularity = 31F
                    formatMonth(date)
                }
                else -> {
                    chart.xAxis.granularity = 1F
                    formatDay(date)
                }
            }
        }

        fun determineDate(xValue: Float, totalValues: Int, lastDate: Date?): Date {
            val diff: Int = totalValues - xValue.toInt()
            val cal = Calendar.getInstance(Locale.US)
            lastDate?.let {
                cal.time = lastDate
            }
            cal.add(Calendar.DATE, -diff)
            return cal.time
        }

        private fun formatQuarter(date: Date): String {
            val cal = Calendar.getInstance(Locale.US)
            cal.time = date
            val quarter = cal.get(Calendar.MONTH) / 3 + 1
            return "${SimpleDateFormat("yy", Locale.US).format(date)}/Q$quarter"
        }

        private fun formatMonth(date: Date): String {
            return SimpleDateFormat("yy/MM", Locale.US).format(date)
        }

        private fun formatDay(date: Date): String {
            return SimpleDateFormat("MM/dd", Locale.US).format(date)
        }
    }
}