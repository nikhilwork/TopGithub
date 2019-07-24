package com.appstreet.topgithub.model

import com.appstreet.topgithub.model.TrendingDeveloper
import com.appstreet.topgithub.webapi.WebApiInterface
import io.reactivex.Observable


class DevelopersRepository(val webApiInterface: WebApiInterface) {

    /**
     * method to call github trending developers list api
     * @param language - language of developers list
     * @param since - monthly or weekly or daily etc
     */
    fun executeDevelopersRepositoryApi(language: String, since: String): Observable<List<TrendingDeveloper>> {
        return webApiInterface.getTrendingRepositories(language, since)
    }

}