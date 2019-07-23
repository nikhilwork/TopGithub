package com.appstreet.topgithub.di

import android.app.Application
import android.content.Context
import com.appstreet.topgithub.network.WebApiInterface
import com.appstreet.topgithub.ui.viewmodel.DevelopersRepository
import com.appstreet.topgithub.utils.AppConstants.Companion.HTTP_CONNECT_TIMEOUT
import com.appstreet.topgithub.utils.AppConstants.Companion.HTTP_READ_TIMEOUT
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesApplicationContext(app: Application): Context {
        return app
    }

    @Singleton
    @Provides
    internal fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor
    }

    @Singleton
    @Provides
    internal fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }


    @Provides
    @Named("BaseURL")
    fun providesBaseURL(): String {
        return "https://github-trending-api.now.sh/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named("BaseURL") baseURL: String, jsonConverter: Converter.Factory, callAdapter: CallAdapter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(jsonConverter)
            .addCallAdapterFactory(callAdapter)
            .baseUrl(baseURL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideWebApiInterface(retrofit: Retrofit): WebApiInterface {
        return retrofit.create(WebApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideDevelopersRepository(webApiInterface: WebApiInterface): DevelopersRepository {
        return DevelopersRepository(webApiInterface)
    }

}