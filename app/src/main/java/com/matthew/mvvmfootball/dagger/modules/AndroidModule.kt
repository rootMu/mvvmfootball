package com.matthew.mvvmfootball.dagger.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Android Specific Dependencies
 */
@Module
class AndroidModule {

    /**
     * Provide android context
     */
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}