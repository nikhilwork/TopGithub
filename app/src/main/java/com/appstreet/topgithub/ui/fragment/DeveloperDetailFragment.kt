package com.appstreet.topgithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.appstreet.topgithub.R
import com.appstreet.topgithub.databinding.FragmentDeveloperDetailBinding
import com.appstreet.topgithub.imagelib.ImageLibXCore
import com.appstreet.topgithub.model.TrendingDeveloper
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_developer_detail.*
import javax.inject.Inject


class DeveloperDetailFragment: DaggerFragment() {
    lateinit var fragmentBinding : FragmentDeveloperDetailBinding
    lateinit var developer: TrendingDeveloper
    @Inject
    lateinit var imageLibXCore: ImageLibXCore
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_developer_detail, container, false);
        if (arguments?.containsKey(KEY_DEVELOPER_DETAIL)!!) {
            arguments?.let { developer =  it.getParcelable(KEY_DEVELOPER_DETAIL)}
        }
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.developer = developer
        developer.avatar?.let { imageLibXCore.loadBitmap(it, ivAvatar, R.mipmap.ic_launcher) }
    }

    companion object {

        private const val KEY_DEVELOPER_DETAIL = "developer_detail"
        private const val TRANSITION_NAME = "transition_name"

        fun create(trendingDeveloper: TrendingDeveloper) =
            DeveloperDetailFragment().apply {
                arguments = Bundle().apply { putParcelable(KEY_DEVELOPER_DETAIL, trendingDeveloper) }
            }
    }
}
