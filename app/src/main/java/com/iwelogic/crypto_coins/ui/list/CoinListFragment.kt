package com.iwelogic.crypto_coins.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.iwelogic.crypto_coins.databinding.FragmentCoinListBinding
import kotlinx.android.synthetic.main.fragment_coin_list.*
import androidx.lifecycle.Observer
import com.iwelogic.crypto_coins.models.Coin

import androidx.navigation.fragment.findNavController
import com.iwelogic.crypto_coins.R

class CoinListFragment : Fragment() {

    private lateinit var viewModel: CoinListViewModel
    var binding : FragmentCoinListBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CoinListViewModel::class.java)
        if(binding == null){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_list, container, false)
            binding!!.viewModel = viewModel
            viewModel.load()
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(list.adapter == null){
            list.adapter = CoinAdapter(viewModel.mCoins)
            (list.adapter as CoinAdapter).onItemClick = {
                findNavController().navigate(CoinListFragmentDirections.coinDetails(it))
            }
            viewModel.mCoins.observe(this, Observer<List<Coin>> { coins: List<Coin> ->
                (list.adapter as CoinAdapter).notifyDataSetChanged()
            })
        }
    }
}
