package com.iwelogic.crypto_coins.ui.coin_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.iwelogic.crypto_coins.R
import com.iwelogic.crypto_coins.databinding.FragmentCoinDetailsBinding
import com.iwelogic.crypto_coins.models.Coin
import kotlinx.android.synthetic.main.fragment_coin_details.*

class CoinDetailsFragment : Fragment() {

    private lateinit var viewModel: CoinDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CoinDetailsViewModel::class.java)
        val binding: FragmentCoinDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_details, container, false)
        binding.viewModel = viewModel
        return binding.root
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coin: Coin = CoinDetailsFragmentArgs.fromBundle(arguments!!).coin
        viewModel.coin = coin
        viewModel.load()

        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
