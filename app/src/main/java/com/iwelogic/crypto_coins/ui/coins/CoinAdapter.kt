package com.iwelogic.crypto_coins.ui.coins

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
import com.iwelogic.crypto_coins.databinding.ItemAdCoinsBinding
import com.iwelogic.crypto_coins.databinding.ItemCoinBinding
import com.iwelogic.crypto_coins.models.Coin
import kotlinx.android.synthetic.main.item_ad_coins.view.*

class CoinAdapter(items: List<Coin>, private val onClick: ((Coin, TextView, ImageView) -> Unit)) : BaseRecyclerAdapter<Coin>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            CoinHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_coin, parent, false))
        } else {
            AdHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_ad_coins, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CoinHolder){
            holder.bind(getItem(position))
        } else {
            (holder as AdHolder).bind()
        }
    }

    internal inner class CoinHolder(private val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.coin = coin
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(coin, binding.root.findViewById(R.id.name),  binding.root.findViewById(R.id.image))
            }
        }
    }

    internal inner class AdHolder(private val binding: ItemAdCoinsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val adView = binding.root.adView
            if(adView.tag == null || adView.tag !is Boolean || !(adView.tag as Boolean)){
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
                adView.loadAd(AdRequest.Builder().addTestDevice("B6D402A58A4DC72BD8A2E0CB2F401652").build())
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isAd ) 0 else 1
    }
}