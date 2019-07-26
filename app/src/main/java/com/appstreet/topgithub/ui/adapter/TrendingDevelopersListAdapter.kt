package com.appstreet.topgithub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.topgithub.BR
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.listener.ItemClickListener
import android.view.animation.ScaleAnimation
import android.view.animation.Animation
import android.widget.ImageView
import com.appstreet.topgithub.databinding.RowTrendingDeveloperBinding
import com.appstreet.topgithub.imagelib.ImageLibXCore


class TrendingDevelopersListAdapter(
    val developersList : List<TrendingDeveloper>,
    private val imageLibXCore: ImageLibXCore,
    val isAnimationNeeded: Boolean,
    val listener: ItemClickListener
): RecyclerView.Adapter<TrendingDevelopersListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemRowBinding: RowTrendingDeveloperBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.row_trending_developer, parent, false)
        return MyViewHolder(itemRowBinding)
    }

    override fun getItemCount(): Int {
        return developersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(developersList.get(position))
        if (isAnimationNeeded) setScaleAnimation(holder.itemView)
    }

    inner class MyViewHolder(var itemRowBinding: RowTrendingDeveloperBinding) :
        RecyclerView.ViewHolder(itemRowBinding.getRoot()){

        fun bind(trendingDeveloper: TrendingDeveloper) {
            itemRowBinding.setVariable(BR.position, adapterPosition)
            itemRowBinding.setVariable(BR.developer, trendingDeveloper)
            itemRowBinding.setVariable(BR.itemClickListener, listener)
            itemRowBinding.executePendingBindings()
            developersList[adapterPosition].avatar?.let { imageLibXCore.loadBitmap(it, itemRowBinding.ivAvatar, R.mipmap.ic_launcher) }
        }
    }


    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = 600
        view.startAnimation(anim)
    }
}