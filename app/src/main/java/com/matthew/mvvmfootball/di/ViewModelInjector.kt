package com.matthew.mvvmfootball.di

import com.matthew.mvvmfootball.modules.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * injects the required dependencies into specified viewModel
     * @param listViewModel listViewModel in which to inject dependencies
     */
    fun inject(listViewModel: ListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}