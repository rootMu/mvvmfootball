package com.matthew.mvvmfootball.dagger.modules

import com.matthew.mvvmfootball.ListActivity
import com.matthew.mvvmfootball.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * All Activities being injected into
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeListActivity(): ListActivity
}