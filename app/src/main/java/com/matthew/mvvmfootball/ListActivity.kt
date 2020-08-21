package com.matthew.mvvmfootball

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.matthew.mvvmfootball.databinding.ActivityListBinding
import com.matthew.mvvmfootball.modules.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }


}