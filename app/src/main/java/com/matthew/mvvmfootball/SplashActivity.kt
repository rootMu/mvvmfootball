package com.matthew.mvvmfootball

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.matthew.mvvmfootball.databinding.ActivitySplashBinding
import com.matthew.mvvmfootball.modules.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel.apply {
            launchList.observe(this@SplashActivity, Observer { launchListActivity() })
        }
        binding.lifecycleOwner = this
    }

    private fun launchListActivity() {
        startActivity(Intent(applicationContext, ListActivity::class.java))
        finish()
    }

}