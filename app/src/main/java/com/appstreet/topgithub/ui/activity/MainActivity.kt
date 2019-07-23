package com.appstreet.topgithub.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.appstreet.topgithub.R
import com.appstreet.topgithub.ui.fragment.TrendingDevelopersFragment
import com.appstreet.topgithub.ui.viewmodel.DeveloperViewModel
import com.appstreet.topgithub.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(TrendingDevelopersFragment(),TrendingDevelopersFragment::class.java.simpleName);
    }

    private fun addFragment(fragment: Fragment, fragmentTag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.flContainer,TrendingDevelopersFragment(), fragmentTag)
        fragmentTransaction.addToBackStack(fragmentTag)
        fragmentTransaction.commit()
    }




}
