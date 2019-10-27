package com.iwelogic.crypto_coins.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>(private val items: List<T>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    protected fun getItem(position: Int): T {
        return items[position]
    }
}