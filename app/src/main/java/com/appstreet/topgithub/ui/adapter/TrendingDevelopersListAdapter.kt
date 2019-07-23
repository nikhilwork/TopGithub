package com.appstreet.topgithub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper


class TrendingDevelopersListAdapter(private val developersList : List<TrendingDeveloper>): RecyclerView.Adapter<TrendingDevelopersListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_trending_developer, parent, false)

        PostR
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return developersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Not needed right now
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding: Post? = null

    }
}