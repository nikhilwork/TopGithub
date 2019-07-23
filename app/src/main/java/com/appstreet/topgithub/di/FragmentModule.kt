package com.appstreet.topgithub.di

import com.appstreet.topgithub.ui.activity.MainActivity
import com.appstreet.topgithub.ui.fragment.TrendingDevelopersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesTrendingDevelopersFragment(): TrendingDevelopersFragment

}