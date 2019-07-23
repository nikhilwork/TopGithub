package com.appstreet.topgithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.adapter.TrendingDevelopersListAdapter
import com.appstreet.topgithub.ui.viewmodel.DeveloperViewModel
import com.appstreet.topgithub.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_trending_developers.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration


class TrendingDevelopersFragment: DaggerFragment() {
    lateinit var viewModel : DeveloperViewModel
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    var developersList  = ArrayList<TrendingDeveloper>()
    lateinit var adapter: TrendingDevelopersListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trending_developers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeveloperViewModel::class.java)
        setRecyclerViewAdapter()
        viewModel.callDevelopersRepositoryApi("java","weekly")
        viewModel.getDevelopersList().observe(this, object : Observer<List<TrendingDeveloper>>{
            override fun onChanged(data: List<TrendingDeveloper>?) {
                developersList.clear()
                data?.let { developersList.addAll(it) }
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setRecyclerViewAdapter() {
        val layoutManager = LinearLayoutManager(context)
        rvTrendingDevelopers.layoutManager = layoutManager
        adapter = TrendingDevelopersListAdapter(developersList)
        val dividerItemDecoration = DividerItemDecoration(
            rvTrendingDevelopers.getContext(),
            layoutManager.getOrientation()
        )
        rvTrendingDevelopers.addItemDecoration(dividerItemDecoration)
        rvTrendingDevelopers.adapter = adapter
    }
}