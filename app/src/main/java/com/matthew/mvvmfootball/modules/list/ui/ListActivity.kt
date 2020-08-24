package com.matthew.mvvmfootball.modules.list.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.databinding.ActivityListBinding
import com.matthew.mvvmfootball.modules.list.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_list.*

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_list
        )
        binding.viewModel = viewModel.apply {
            adapter = FootballAdapter(this@ListActivity)
        }
        binding.lifecycleOwner = this
    }


}