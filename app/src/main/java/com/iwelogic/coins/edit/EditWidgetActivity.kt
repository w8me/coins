package com.iwelogic.coins.edit

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.iwelogic.coins.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.iwelogic.coins.databinding.ActivityEditWidgetBinding
import com.iwelogic.coins.widget.CoinWidget
import com.iwelogic.coins.widget.WidgetService.Companion.REFRESH_WIDGET
import kotlinx.android.synthetic.main.activity_edit_widget.*


class EditWidgetActivity : AppCompatActivity() {

    lateinit var viewModel: EditWidgetViewModel
     var mWidgetId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_widget)

        viewModel = ViewModelProviders.of(this).get(EditWidgetViewModel::class.java)

        val binding : ActivityEditWidgetBinding  = DataBindingUtil.setContentView(this, R.layout.activity_edit_widget)
        binding.viewModel = viewModel

        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)

        viewModel.config.observe(this, Observer {
            binding.viewModel = viewModel
        })

        save.setOnClickListener {
            viewModel.clickSave()

            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId)
            setResult(Activity.RESULT_OK, resultValue)

            val refresh = Intent(this@EditWidgetActivity, CoinWidget::class.java)
            refresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId)
            refresh.action = REFRESH_WIDGET
            sendBroadcast(refresh)

            finish()
        }

        panelColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this@EditWidgetActivity)
                .setTitle("Choose color")
                .initialColor(Color.BLUE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok") { dialog, selectedColor, allColors ->
                    viewModel.setBackgroundColor(selectedColor, Color.alpha(selectedColor), Color.rgb(Color.red(selectedColor), Color.green(selectedColor), Color.blue(selectedColor)))
                }
                .build()
                .show()
        }

        viewModel.widgetId = mWidgetId
    }
}
