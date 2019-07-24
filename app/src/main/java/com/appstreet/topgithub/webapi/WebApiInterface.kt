package com.appstreet.topgithub.webapi

import com.appstreet.topgithub.model.TrendingDeveloper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WebApiInterface {
    @GET("developers")
    fun getTrendingRepositories(@Query("language") language: String, @Query("since") since: String): Observable<List<TrendingDeveloper>>
}