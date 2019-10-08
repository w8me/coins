package com.iwelogic.coins.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.coins.R
import com.iwelogic.coins.base.BaseRecyclerAdapter
import com.iwelogic.coins.databinding.ItemCoinBinding
import com.iwelogic.coins.models.Coin

class CoinAdapter(items: MutableLiveData<MutableList<Coin>>) : BaseRecyclerAdapter<Coin>(items) {

    var onItemClick: ((Coin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CoinHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_coin, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CoinHolder).bind(getItem(position))
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
}