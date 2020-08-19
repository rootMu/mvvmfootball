package com.matthew.mvvmfootball.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.matthew.mvvmfootball.BuildConfig.BASE_URL
import com.matthew.mvvmfootball.network.FootballApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {

    /**
     * Provides the AlbumApi implementation
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Album Api implementation
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideFootballApi(retrofit: Retrofit): FootballApi {
        return retrofit.create(FootballApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @param okHttpClient the OkHttpClient object
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides the OkHttpClient object.
     * @param interceptor the HttpLoggingInterceptor object
     * @return the OkHttpClient object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        b.addInterceptor(interceptor)
        return b.build()
    }

    /**
     * Provides the HttpLoggingInterceptor object.
     * @return the HttpLoggingInterceptor object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}