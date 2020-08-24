package com.matthew.mvvmfootball.ui.list.recyclerview

import androidx.lifecycle.LiveData

data class UiPlayer(
    override var name: String = "",
    var age: String = "",
    var club: String = "",
    var nationality: String = "",
    var visibility: LiveData<Boolean>
) : UiModel
