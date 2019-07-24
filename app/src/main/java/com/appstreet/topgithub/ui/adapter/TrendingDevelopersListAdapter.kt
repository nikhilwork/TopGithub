package com.appstreet.topgithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.topgithub.BR
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.listener.ItemClickListener


class TrendingDevelopersListAdapter(private val developersList : List<TrendingDeveloper>): RecyclerView.Adapter<TrendingDevelopersListAdapter.MyViewHolder>(), ItemClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemRowBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.row_trending_developer, parent, false)
        return MyViewHolder(itemRowBinding)
    }

    override fun getItemCount(): Int {
        return developersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(developersList.get(position))
    }

    inner class MyViewHolder(var itemRowBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemRowBinding.getRoot()) {

        fun bind(obj: Any) {
            itemRowBinding.setVariable(BR.developer, obj)
            itemRowBinding.executePendingBindings()
        }
    }

    override fun itemClicked(trendingDeveloper: TrendingDeveloper) {

    }
}