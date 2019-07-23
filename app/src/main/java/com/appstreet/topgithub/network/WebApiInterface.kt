package com.appstreet.topgithub.network

import com.appstreet.topgithub.model.TrendingDeveloper
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WebApiInterface {
    @GET("developers")
    fun getTrendingRepositories(@Query("language") language: String, @Query("since") since: String): Observable<List<TrendingDeveloper>>
}