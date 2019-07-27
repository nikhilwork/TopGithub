package com.appstreet.topgithub

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.appstreet.topgithub.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewInstrumentedTest {

    @get:Rule
    var activityActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    /*@Before
    fun init() {
        activityActivityTestRule.activity.supportFragmentManager.beginTransaction()
    }*/

    @Test
    fun recyclerViewScrollTest() {
        Thread.sleep(1000)

        onView(withId(R.id.rvTrendingDevelopers)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }


    @Test
    fun recyclerViewClickTest() {
        Thread.sleep(3000)

        onView(withId(R.id.rvTrendingDevelopers)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(2000)
        pressBack()

        onView(withId(R.id.rvTrendingDevelopers)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        Thread.sleep(2000)
    }
}