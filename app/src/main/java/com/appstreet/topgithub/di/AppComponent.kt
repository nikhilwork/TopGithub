package com.appstreet.topgithub.di

import android.app.Application
import com.appstreet.topgithub.TopGithubApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, FragmentModule::class, ViewModelModule::class])
interface AppComponent: AndroidInjector<TopGithubApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}