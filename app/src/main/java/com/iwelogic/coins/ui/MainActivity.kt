package com.iwelogic.coins.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.iwelogic.coins.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(this, R.id.mainHostFragment)

        navController.addOnDestinationChangedListener {
            controller, destination, arguments ->
            toolbar.title = navController.currentDestination?.label
        }
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.mainHostFragment).navigateUp()

    fun updateTitle(title : String){
        toolbar.title = title
    }
}
