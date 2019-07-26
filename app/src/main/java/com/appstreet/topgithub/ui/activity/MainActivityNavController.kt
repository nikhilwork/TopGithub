package com.appstreet.topgithub.ui.activity

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.ui.fragment.DeveloperDetailFragment
import com.appstreet.topgithub.ui.fragment.TrendingDevelopersFragment
import javax.inject.Inject

class MainActivityNavController @Inject constructor(var activity: MainActivity) {

    private val containerID = R.id.flContainer
    private val fm = activity.supportFragmentManager

    fun navigateToTrendingDevelopers() {
        addFragment(TrendingDevelopersFragment.create(), null)
    }

    /**
     * @param trendingDeveloper - trending developer object that will display on detail fragment
     */
    fun navigateToDeveloperDetail(trendingDeveloper: TrendingDeveloper, imageView: ImageView) {
        addFragment(DeveloperDetailFragment.create(trendingDeveloper, imageView.transitionName,activity), imageView)
    }

    /**
     * A function to add a fragment in activity
     * @param fragment - the instance of fragment that need to add
     */
    private fun addFragment(fragment: Fragment, imageView: ImageView?) {
        val fragmentTransaction = fm.beginTransaction()
        imageView?.let {fragmentTransaction.addSharedElement(it,it.transitionName)}
        fragmentTransaction.replace(containerID, fragment, fragment::class.java.simpleName)
        fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        fragmentTransaction.commit()
    }

}