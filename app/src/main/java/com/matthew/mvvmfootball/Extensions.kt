package com.matthew.mvvmfootball

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun View.getParentActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}