package com.appstreet.topgithub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.appstreet.topgithub.R
import com.appstreet.topgithub.model.DevelopersRepository
import com.appstreet.topgithub.model.Resource
import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.utils.InternetConnectionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DeveloperViewModel @Inject constructor(val repository: DevelopersRepository): ViewModel() {
    @Inject
    lateinit var internetConnectionManager: InternetConnectionManager
    private val disposables = CompositeDisposable()
    private val developersLiveData = MutableLiveData<Resource<List<TrendingDeveloper>>>()

    /**
     * method to get trending developers list as live data
     * @return trending developers live data
     */
    fun getDevelopersList(): LiveData<Resource<List<TrendingDeveloper>>> {
        return developersLiveData
    }

    /**
     * method to call trending developers list api
     * @param language - language of developers list
     * @param since - monthly or weekly or daily etc
     */
    fun callDevelopersRepositoryApi(language: String, since: String) {
        if (internetConnectionManager.isNetworkAvailable()) {
            developersLiveData.value = Resource.Loading(null)
            disposables.add(repository.executeDevelopersRepositoryApi(language, since)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { d -> developersLiveData.setValue(Resource.Loading(null)) }
                .subscribe(
                    { result -> developersLiveData.setValue(Resource.Success(result)) },
                    { throwable ->
                        developersLiveData.setValue(
                            Resource.Error(throwable.message ?: "Unknown Error")
                        )
                    }
                ))
        } else {
            developersLiveData.setValue(
                Resource.InternetError(R.string.internet_connection_error_message)
            )
        }
    }
    override fun onCleared() {
        super.onCleared()
        disposables.clear() // Clear all observers that added in composite disposable
    }
}