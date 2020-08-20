package com.matthew.mvvmfootball

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FootballApplication : Application()//, HasAndroidInjector {
//
//    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>
//
//    override fun onCreate() {
//        super.onCreate()
//        DaggerAppComponent.builder() // Building the app component
//            .application(this)
//            .build()
//            .inject(this) // Injecting our android injector
//    }
//
//    override fun androidInjector(): AndroidInjector<Any> = androidInjector
//}
