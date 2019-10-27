package com.iwelogic.crypto_coins.ui.news

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
import com.iwelogic.crypto_coins.databinding.FragmentNewsListBinding
import com.iwelogic.crypto_coins.models.News
import com.iwelogic.crypto_coins.ui.main.MainFragmentDirections
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : Fragment(), NewsListNavigation {

    private lateinit var viewModel: NewsListViewModel
    private var binding: FragmentNewsListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)
            binding?.viewModel = viewModel
            viewModel.navigation = this
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

    override fun openDetails(data: News, imageView: ImageView, title: TextView, body: TextView) {
        try {
            val extras = FragmentNavigatorExtras(
                imageView to data.id!!,
                title to data.title!!,
                body to data.body!!
            )
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNewsDetailsFragment(data), extras)
        } catch (e: Exception){
            e.printStackTrace()
        }

    }
}
