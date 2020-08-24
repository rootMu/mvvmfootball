package com.matthew.mvvmfootball.modules.list.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.databinding.ActivityListBinding
import com.matthew.mvvmfootball.modules.list.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity(), OnNetworkErrorSelect {

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
        binding.onNetworkErrorSelect = this@ListActivity
        binding.lifecycleOwner = this
    }

    override fun errorDialog() =
        AlertDialog.Builder(this).apply {
            setTitle("Network Error")
            setMessage("A network connection is required to run this app effectively")
        }

}