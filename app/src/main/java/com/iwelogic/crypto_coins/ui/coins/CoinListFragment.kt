package com.iwelogic.crypto_coins.ui.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.iwelogic.crypto_coins.R
import com.iwelogic.crypto_coins.databinding.FragmentCoinListBinding
import com.iwelogic.crypto_coins.models.Coin
import com.iwelogic.crypto_coins.ui.main.MainFragmentDirections
import kotlinx.android.synthetic.main.fragment_news_list.*

class CoinListFragment : Fragment(), CoinListNavigator {

    private lateinit var viewModel: CoinListViewModel
    private var binding: FragmentCoinListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CoinListViewModel::class.java)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_list, container, false)
            binding?.viewModel = viewModel
            viewModel.navigator = this
            viewModel.load()
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.apply {
            parentFragment?.postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                parentFragment?.startPostponedEnterTransition()
                true
            }
        }
    }

    override fun openDetails(coin: Coin, name: TextView, image: ImageView) {
        try {
            val extras = FragmentNavigatorExtras(
                name to coin.name,
                image to coin.image
            )
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCoinDetailsFragment(coin), extras)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
