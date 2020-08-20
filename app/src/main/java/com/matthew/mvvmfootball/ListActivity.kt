package com.matthew.mvvmfootball

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.matthew.mvvmfootball.databinding.ActivityListBinding
import com.matthew.mvvmfootball.modules.ListViewModel

class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)

        setContentView(R.layout.activity_list)
    }


}