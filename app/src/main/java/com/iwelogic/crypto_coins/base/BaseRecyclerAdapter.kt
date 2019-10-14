package com.iwelogic.crypto_coins.base

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>(private val items: MutableLiveData<MutableList<T>>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.value!!.size
    }

    protected fun getItem(position: Int): T {
        return items.value!![position]
    }
}