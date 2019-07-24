package com.appstreet.topgithub.ui.listener

import com.appstreet.topgithub.model.TrendingDeveloper

interface ItemClickListener {
    fun itemClicked(trendingDeveloper: TrendingDeveloper)
}