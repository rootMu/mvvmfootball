package com.matthew.mvvmfootball

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.matthew.mvvmfootball.databinding.ActivityListBinding
import com.matthew.mvvmfootball.modules.ListViewModel

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        setContentView(R.layout.activity_list)
    }


}