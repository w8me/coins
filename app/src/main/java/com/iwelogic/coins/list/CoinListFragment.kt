package com.iwelogic.coins.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.iwelogic.coins.databinding.FragmentCoinListBinding
import kotlinx.android.synthetic.main.fragment_coin_list.*
import androidx.lifecycle.Observer
import com.iwelogic.coins.models.Coin

import androidx.navigation.fragment.findNavController
import com.iwelogic.coins.R

class CoinListFragment : Fragment() {

    private lateinit var viewModel: CoinListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CoinListViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentCoinListBinding>(inflater, R.layout.fragment_coin_list, container, false).apply {
            viewModelFrag = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = CoinAdapter(viewModel.mCoins)
        (list.adapter as CoinAdapter).onItemClick = {
            findNavController().navigate(CoinListFragmentDirections.coinDetails(it))
        }
        viewModel.mCoins.observe(this, Observer<List<Coin>> { coins: List<Coin> ->
            (list.adapter as CoinAdapter).notifyDataSetChanged()
        })
        viewModel.load()
    }
}
