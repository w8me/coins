package com.iwelogic.crypto_coins.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.iwelogic.crypto_coins.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAdapter = MainPagerAdapter(childFragmentManager, lifecycle)
        viewpager.adapter = mAdapter

        TabLayoutMediator(tabs, viewpager, true) { tab, position ->
            tab.text = mAdapter.getPageTitle(position)
        }.attach()

        for (x in 0 until tabs.tabCount step 1){
            val title : TextView = LayoutInflater.from(context).inflate(R.layout.item_tab, null) as TextView
            title.text = mAdapter.getPageTitle(x)
            tabs.getTabAt(x)?.setCustomView(title)
        }
    }
}
