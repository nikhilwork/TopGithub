package com.appstreet.topgithub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.appstreet.topgithub.model.TrendingDeveloper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DeveloperViewModel @Inject constructor(val repository: DevelopersRepository): ViewModel() {
    private val disposables = CompositeDisposable()
    private val developersLiveData = MutableLiveData<List<TrendingDeveloper>>()

    /*
    * return live data
     */
    fun getDevelopersList(): LiveData<List<TrendingDeveloper>> {
        return developersLiveData
    }

    /*
    * method to call get trending developers list api with $(language + since)
    * */
    fun callDevelopersRepositoryApi(language: String, since: String) {

        disposables.add(repository.executeDevelopersRepositoryApi(language, since)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe({ d -> /*developersLiveData.setValue(AppConstants.loading())*/ })
            .subscribe(
                { result -> developersLiveData.setValue(result) },
                { throwable -> developersLiveData.setValue(null)}
            ))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}