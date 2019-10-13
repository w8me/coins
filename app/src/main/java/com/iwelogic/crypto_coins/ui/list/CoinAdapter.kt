package com.iwelogic.crypto_coins.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.iwelogic.crypto_coins.R
import com.iwelogic.crypto_coins.base.BaseRecyclerAdapter
import com.iwelogic.crypto_coins.databinding.ItemAdBinding
import com.iwelogic.crypto_coins.databinding.ItemCoinBinding
import com.iwelogic.crypto_coins.models.Coin
import kotlinx.android.synthetic.main.item_ad.view.*

class CoinAdapter(items: MutableLiveData<MutableList<Coin>>) : BaseRecyclerAdapter<Coin>(items) {

    var onItemClick: ((Coin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            return CoinHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_coin, parent, false))
        } else {
            return AdHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_ad, parent, false))
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
                onItemClick?.invoke(coin)
            }
        }
    }

    internal inner class AdHolder(private val binding: ItemAdBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.root.adView.loadAd(AdRequest.Builder().addTestDevice("B6D402A58A4DC72BD8A2E0CB2F401652").build())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isAd ) 0 else 1
    }
}