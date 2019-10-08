package com.iwelogic.coins.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.iwelogic.coins.MainActivity
import com.iwelogic.coins.R
import com.iwelogic.coins.databinding.FragmentCoinDetailsBinding
import com.iwelogic.coins.models.Coin
import kotlinx.android.synthetic.main.fragment_coin_details.*

class CoinDetailsFragment : Fragment() {

    private lateinit var viewModel: CoinDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CoinDetailsViewModel::class.java)

        val binding: FragmentCoinDetailsBinding = DataBindingUtil.inflate<FragmentCoinDetailsBinding>(inflater, R.layout.fragment_coin_details, container, false).apply {
            viewModelFrag = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coin: Coin = CoinDetailsFragmentArgs.fromBundle(arguments!!).coin
        coin.name?.let { (activity as MainActivity).updateTitle(it) }
        title.text = coin.name
    }
}
