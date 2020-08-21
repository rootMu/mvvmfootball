package com.matthew.mvvmfootball.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

object NetworkUtil {

    fun isInternetAvailable(context: Context) = liveData<Boolean> {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities)
            result = actNw?.let{
                when {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }?: false
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        emit(result)
    }

}
