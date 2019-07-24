package com.appstreet.topgithub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.topgithub.BR
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.listener.FragmentCallListener
import com.appstreet.topgithub.ui.listener.ItemClickListener
import android.view.animation.AlphaAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.Animation


class TrendingDevelopersListAdapter(val developersList : List<TrendingDeveloper>, val fragmentCallListener : FragmentCallListener): RecyclerView.Adapter<TrendingDevelopersListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemRowBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.row_trending_developer, parent, false)
        return MyViewHolder(itemRowBinding)
    }

    override fun getItemCount(): Int {
        return developersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(developersList.get(position))
        setScaleAnimation(holder.itemView)
    }

    inner class MyViewHolder(var itemRowBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemRowBinding.getRoot()),ItemClickListener {

        fun bind(obj: Any) {
            itemRowBinding.setVariable(BR.developer, obj)
            itemRowBinding.setVariable(BR.itemClickListener, this)
            itemRowBinding.executePendingBindings()
        }

        override fun itemClicked(trendingDeveloper: TrendingDeveloper) {
            fragmentCallListener.callFragment(trendingDeveloper)
        }
    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = 600
        view.startAnimation(anim)
    }
}