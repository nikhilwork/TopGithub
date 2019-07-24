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
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.topgithub.ui.listener.FragmentCallListener
import com.appstreet.topgithub.utils.CommonUtils


class TrendingDevelopersFragment(val fragmentCallListener: FragmentCallListener): DaggerFragment() {
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
        callDevelopersListApi()
        getLiveDataFromViewModel()
    }

    /**
     * Get live data from view model
     * observe data for changes and notify adapter
     */
    private fun getLiveDataFromViewModel() {
        viewModel.getDevelopersList().observe(this, object : Observer<List<TrendingDeveloper>>{
            override fun onChanged(data: List<TrendingDeveloper>?) {
                developersList.clear()
                data?.let { developersList.addAll(it) }
                adapter.notifyDataSetChanged()
                if (developersList.isEmpty()){
                    tvNoDataFound.visibility = View.VISIBLE
                    tvNoDataFound.text = getString(R.string.no_developers_found)
                }else{
                    tvNoDataFound.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Check internet connection and call developers list api else set error message
     */
    private fun callDevelopersListApi() {
        if (CommonUtils.isNetworkAvailable(context!!)){
            tvNoDataFound.visibility = View.GONE
            viewModel.callDevelopersRepositoryApi("java","weekly")
        }else{
            tvNoDataFound.visibility = View.VISIBLE
            tvNoDataFound.text = getString(R.string.internet_connection_error_message)
        }
    }

    /**
     * Set layout manager and adapter to recycler view
     */
    private fun setRecyclerViewAdapter() {
        val layoutManager = LinearLayoutManager(context)
        rvTrendingDevelopers.layoutManager = layoutManager
        adapter = TrendingDevelopersListAdapter(developersList, fragmentCallListener)
        rvTrendingDevelopers.adapter = adapter
    }
}