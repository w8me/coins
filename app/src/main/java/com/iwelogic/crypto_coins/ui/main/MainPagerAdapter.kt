package com.iwelogic.crypto_coins.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iwelogic.crypto_coins.ui.coins.CoinListFragment
import com.iwelogic.crypto_coins.ui.news.NewsListFragment
import java.util.*

class MainPagerAdapter(fm: FragmentManager,lifeCycle: Lifecycle) :
    FragmentStateAdapter(fm, lifeCycle) {

    private val titles = listOf("Coins", "News")
    private var fragments: MutableList<Fragment> = ArrayList()

    init {
        fragments.add(CoinListFragment())
        fragments.add(NewsListFragment())
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getPageTitle(position: Int): CharSequence = titles[position]
}