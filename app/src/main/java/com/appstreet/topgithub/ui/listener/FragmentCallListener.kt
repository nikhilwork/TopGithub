package com.appstreet.topgithub.ui.listener

import com.appstreet.topgithub.model.TrendingDeveloper

interface FragmentCallListener {
    fun callFragment(trendingDeveloper: TrendingDeveloper);
}