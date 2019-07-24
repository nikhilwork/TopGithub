package com.appstreet.topgithub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.appstreet.topgithub.model.DevelopersRepository
import com.appstreet.topgithub.model.TrendingDeveloper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DeveloperViewModel @Inject constructor(val repository: DevelopersRepository): ViewModel() {
    private val disposables = CompositeDisposable()
    private val developersLiveData = MutableLiveData<List<TrendingDeveloper>>()

    /**
     * method to get trending developers list as live data
     * @return trending developers live data
     */
    fun getDevelopersList(): LiveData<List<TrendingDeveloper>> {
        return developersLiveData
    }

    /**
     * method to call trending developers list api
     * @param language - language of developers list
     * @param since - monthly or weekly or daily etc
     */
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
        disposables.clear() // Clear all observers that added in composite disposable
    }
}