package com.appstreet.topgithub.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.fragment.DeveloperDetailFragment
import com.appstreet.topgithub.ui.fragment.TrendingDevelopersFragment
import com.appstreet.topgithub.ui.listener.FragmentCallListener
import com.appstreet.topgithub.ui.viewmodel.DeveloperViewModel
import com.appstreet.topgithub.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), FragmentCallListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(TrendingDevelopersFragment(this), TrendingDevelopersFragment::class.java.simpleName);
    }

    private fun addFragment(fragment: Fragment, fragmentTag: String, addToBackstack: Boolean = true) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.flContainer, fragment, fragmentTag)
        fragmentTransaction.addToBackStack(fragmentTag)
        fragmentTransaction.commit()
    }

    override fun callFragment(trendingDeveloper: TrendingDeveloper) {
        val fragmentDetail = DeveloperDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("developer_detail", trendingDeveloper)
        fragmentDetail.arguments = bundle
        addFragment(fragmentDetail, DeveloperDetailFragment::class.java.simpleName);
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        }else{
            finish()
        }
    }
}
