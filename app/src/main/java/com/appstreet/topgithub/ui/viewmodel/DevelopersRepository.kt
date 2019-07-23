package com.appstreet.topgithub.ui.viewmodel

import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.network.WebApiInterface
import io.reactivex.Observable


class DevelopersRepository(val webApiInterface: WebApiInterface) {
    /*
     * method to call login api
     * */
    fun executeDevelopersRepositoryApi(language: String, since: String): Observable<List<TrendingDeveloper>> {
        return webApiInterface.getTrendingRepositories(language, since)
    }

}