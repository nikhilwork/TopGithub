package com.appstreet.topgithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.appstreet.topgithub.R
import com.appstreet.topgithub.databinding.FragmentDeveloperDetailBinding
import com.appstreet.topgithub.model.TrendingDeveloper


class DeveloperDetailFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding : FragmentDeveloperDetailBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_developer_detail, container, false);
        fragmentBinding.developer = arguments?.getParcelable("developer_detail")
        return fragmentBinding.root
    }
}
