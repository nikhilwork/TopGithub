package com.appstreet.topgithub.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.fragment.DeveloperDetailFragment
import com.appstreet.topgithub.ui.fragment.TrendingDevelopersFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navController: MainActivityNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController.navigateToTrendingDevelopers()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        }else{
            finish()
        }
    }
}
