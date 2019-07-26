package com.appstreet.topgithub.ui.listener

import android.widget.ImageView
import com.appstreet.topgithub.model.TrendingDeveloper

interface ItemClickListener {
    fun itemClicked(trendingDeveloper: TrendingDeveloper, imageView: ImageView)
}