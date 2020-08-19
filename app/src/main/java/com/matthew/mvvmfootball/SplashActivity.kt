package com.matthew.mvvmfootball

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matthew.mvvmfootball.databinding.ActivitySplashBinding
import com.matthew.mvvmfootball.modules.ListViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = ViewModelProvider(this).get(ListViewModel::class.java).apply{
            launchList.observe(this@SplashActivity, Observer { launchListActivity() })
        }
        binding.lifecycleOwner = this
    }

    private fun launchListActivity(){
        startActivity(Intent(applicationContext, ListActivity::class.java))
        finish()
    }

}