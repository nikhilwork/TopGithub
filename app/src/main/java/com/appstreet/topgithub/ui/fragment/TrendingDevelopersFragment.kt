package com.appstreet.topgithub.ui.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.appstreet.topgithub.R
import com.appstreet.topgithub.imagelib.ImageLibXCore
import com.appstreet.topgithub.model.Resource
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.activity.MainActivityNavController
import com.appstreet.topgithub.ui.adapter.TrendingDevelopersListAdapter
import com.appstreet.topgithub.ui.listener.ItemClickListener
import com.appstreet.topgithub.ui.viewmodel.DeveloperViewModel
import com.appstreet.topgithub.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_trending_developers.*
import javax.inject.Inject

class TrendingDevelopersFragment : DaggerFragment(), ItemClickListener {

    lateinit var viewModel: DeveloperViewModel
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var navController: MainActivityNavController
    @Inject
    lateinit var imageLibXCore: ImageLibXCore
    var developersList = ArrayList<TrendingDeveloper>()
    lateinit var adapter: TrendingDevelopersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeveloperViewModel::class.java)
        viewModel.callDevelopersRepositoryApi("java", "weekly")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trending_developers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        viewModel.getDevelopersList().observe(viewLifecycleOwner, Observer { handleResult(it) })
    }

    /**
     * Set layout manager and adapter to recycler view
     */
    private fun setRecyclerViewAdapter() {
        val layoutManager = LinearLayoutManager(context)
        rvTrendingDevelopers.layoutManager = layoutManager
        adapter = TrendingDevelopersListAdapter(developersList, imageLibXCore, this)
        rvTrendingDevelopers.adapter = adapter
    }

    private fun handleResult(result: Resource<List<TrendingDeveloper>>) {
        when (result) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                if (result.data != null) {
                    developersList.clear()
                    developersList.addAll(result.data)
                    adapter.notifyDataSetChanged()
                    if (developersList.isEmpty()){
                        tvNoDataFound.visibility = View.VISIBLE
                        tvNoDataFound.text = getString(R.string.no_developers_found)
                    }else{
                        tvNoDataFound.visibility = View.GONE
                    }
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun itemClicked(trendingDeveloper: TrendingDeveloper) {
        navController.navigateToDeveloperDetail(trendingDeveloper)
    }

    companion object {
        fun create() =
            TrendingDevelopersFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}