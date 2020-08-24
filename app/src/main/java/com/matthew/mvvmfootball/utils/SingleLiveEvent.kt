package com.matthew.mvvmfootball.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T?>() {

    private val isPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner, Observer { newValue: T? ->
            if(isPending.compareAndSet(true, false)){
                observer.onChanged(newValue)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        isPending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call(){
        value = null
    }
}