package com.matthew.mvvmfootball.utils

import androidx.lifecycle.MutableLiveData

class FlipableLiveData(source: Boolean) : MutableLiveData<Boolean>(source) {

    fun flip(){
        value = value?.let{
            !it
        }?:true
    }

}