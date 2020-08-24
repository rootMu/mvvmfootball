package com.matthew.mvvmfootball.ui.list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.matthew.mvvmfootball.R
import com.matthew.mvvmfootball.databinding.ActivityListBinding
import com.matthew.mvvmfootball.ui.list.recyclerview.FootballAdapter
import com.matthew.mvvmfootball.utils.OnNetworkErrorSelect
import com.matthew.mvvmfootball.ui.list.viewmodel.ListViewModel
import com.matthew.mvvmfootball.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity(),
    OnNetworkErrorSelect {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_list
        )
        binding.viewModel = viewModel.apply {
            adapter =
                FootballAdapter(
                    this@ListActivity
                )
            footballDataLiveData.observe(this@ListActivity, Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        mapDataToUi(it.data)
                    }

                    Resource.Status.ERROR -> {
                        mapErrorToUi(it.message)
                        Toast.makeText(this@ListActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Status.LOADING -> {
                        //handle extra loading wheel or something
                    }
                }
            })
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