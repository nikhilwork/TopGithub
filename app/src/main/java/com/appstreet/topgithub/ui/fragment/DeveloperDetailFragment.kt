package com.appstreet.topgithub.ui.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.appstreet.topgithub.R
import com.appstreet.topgithub.databinding.FragmentDeveloperDetailBinding
import com.appstreet.topgithub.imagecachelib.ImageLibXCore
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.activity.MainActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class DeveloperDetailFragment : DaggerFragment() {

    private lateinit var fragmentBinding: FragmentDeveloperDetailBinding
    private lateinit var developer: TrendingDeveloper
    @Inject
    lateinit var imageLibXCore: ImageLibXCore
    private var transitionName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_developer_detail, container, false)
        arguments?.let {
            if (it.containsKey(KEY_DEVELOPER_DETAIL)) {
                developer = it.getParcelable(KEY_DEVELOPER_DETAIL)
            }
            if (it.containsKey(KEY_TRANSITION_NAME)) {
                transitionName = it.getString(KEY_TRANSITION_NAME)
            }
        }
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.developer = developer
        fragmentBinding.ivAvatar.transitionName = transitionName
        developer.avatar?.let { imageLibXCore.loadBitmap(it, fragmentBinding.ivAvatar, R.mipmap.ic_launcher) }
    }

    companion object {

        private const val KEY_DEVELOPER_DETAIL = "developer_detail"
        private const val KEY_TRANSITION_NAME = "transition_name"

        fun create(
            trendingDeveloper: TrendingDeveloper,
            transitionName: String,
            activity: MainActivity
        ): DeveloperDetailFragment {
            val developerDetailFragment = DeveloperDetailFragment()
            developerDetailFragment.apply {
                arguments = Bundle()
                    .apply {
                        putString(KEY_TRANSITION_NAME, transitionName)
                        putParcelable(KEY_DEVELOPER_DETAIL, trendingDeveloper)
                    }
            }
            developerDetailFragment.sharedElementEnterTransition = TransitionInflater.from(activity)
                .inflateTransition(android.R.transition.move)
            return developerDetailFragment
        }
    }
}
