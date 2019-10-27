package com.iwelogic.crypto_coins.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.iwelogic.crypto_coins.R
import com.iwelogic.crypto_coins.base.BaseRecyclerAdapter
import com.iwelogic.crypto_coins.databinding.ItemAdNewsBinding
import com.iwelogic.crypto_coins.databinding.ItemNewsBinding
import com.iwelogic.crypto_coins.models.News
import kotlinx.android.synthetic.main.item_ad_news.view.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(items: List<News>, private val onClick: ((News, ImageView, TextView, TextView) -> Unit)) : BaseRecyclerAdapter<News>(items) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            CoinHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news, parent, false))
        } else {
            AdHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_ad_news, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CoinHolder) {
            holder.bind(getItem(position))
        } else {
            (holder as AdHolder).bind()
        }
    }

    internal inner class CoinHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            binding.news = data
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(data, binding.root.image, binding.root.title, binding.root.body)
            }
        }
    }

    internal inner class AdHolder(private val binding: ItemAdNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val adView = binding.root.adView
            if (adView.tag == null || adView.tag !is Boolean || !(adView.tag as Boolean)) {
                adView.adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        adView.tag = true
                    }

                    override fun onAdFailedToLoad(i: Int) {
                        super.onAdFailedToLoad(i)
                        adView.tag = false
                    }
                }
                adView.loadAd(AdRequest.Builder().build())
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isAd ) 0 else 1
    }

}