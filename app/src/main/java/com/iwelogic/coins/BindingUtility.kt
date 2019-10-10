package com.iwelogic.coins

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import com.cfin.cryptofin.entity.HistoryEntity
import com.github.mikephil.charting.charts.LineChart

class BindingUtility {
    companion object {

        @BindingAdapter("chartData")
        @JvmStatic
        fun setChartData(lineChart: LineChart, history: ObservableList<HistoryEntity>) {
            if (history.size > 0) {
                ChartUtility.setupLineChart(lineChart, history.toList())
                lineChart.visibility = View.VISIBLE
            } else {
                lineChart.visibility = View.GONE
            }
        }
    }
}