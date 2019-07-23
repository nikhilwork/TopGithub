package com.appstreet.topgithub.di

import com.appstreet.topgithub.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributesMainActivity(): MainActivity

}