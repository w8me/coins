package com.iwelogic.crypto_coins.ui.news_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.iwelogic.crypto_coins.R
import com.iwelogic.crypto_coins.databinding.FragmentNewsDetailsBinding
import com.iwelogic.crypto_coins.models.News
import kotlinx.android.synthetic.main.fragment_news_details.*

class NewsDetailsFragment : Fragment(), NewsDetailsNavigator {

    private lateinit var viewModel: NewsDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel::class.java)
        val binding: FragmentNewsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.viewModel = viewModel
        viewModel.navigator = this
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val news: News = NewsDetailsFragmentArgs.fromBundle(arguments!!).news
        viewModel.data.set(news)

        image.postDelayed({ startPostponedEnterTransition() }, 1)
        postponeEnterTransition()

        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun openLink(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
