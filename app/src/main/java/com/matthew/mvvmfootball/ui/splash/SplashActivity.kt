package com.matthew.mvvmfootball.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.databinding.ActivitySplashBinding
import com.matthew.mvvmfootball.ui.list.ListActivity
import com.matthew.mvvmfootball.utils.NetworkUtil

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )

        NetworkUtil.isInternetAvailable(this@SplashActivity)
            .observe(this@SplashActivity, Observer { available ->
                if (available)
                    launchListActivity()
                else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Network Error")
                        setMessage("A network connection is required to run this app effectively")

                        setPositiveButton(android.R.string.ok) { _, _ ->
                            launchListActivity()
                        }

                        setNegativeButton(android.R.string.cancel) { _, _ ->
                            finish()
                        }

                        show()
                    }
                }
            })
    }

    private fun launchListActivity() {
        startActivity(Intent(applicationContext, ListActivity::class.java))
        finish()
    }

}