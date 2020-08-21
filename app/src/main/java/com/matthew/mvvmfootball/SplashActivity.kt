package com.matthew.mvvmfootball

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.matthew.mvvmfootball.databinding.ActivitySplashBinding
import com.matthew.mvvmfootball.utils.NetworkUtil

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        NetworkUtil.isInternetAvailable(this@SplashActivity).observe(this@SplashActivity, Observer { available ->
            if(available)
                launchListActivity()
        })

    }

    private fun launchListActivity() {
        startActivity(Intent(applicationContext, ListActivity::class.java))
        finish()
    }

}