package com.appstreet.topgithub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appstreet.topgithub.ui.viewmodel.DeveloperViewModel
import com.appstreet.topgithub.ui.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DeveloperViewModel::class)
    internal abstract fun bindTrendingDeveloperViewModel(listViewModel: DeveloperViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
