package com.matthew.mvvmfootball.ui.list.recyclerview

import androidx.lifecycle.LiveData
import com.matthew.mvvmfootball.ui.list.recyclerview.UiModel

data class UiClub(
    override var name: String = "",
    var nationality: String = "",
    var homeGround: String = "",
    var city: String = "",
    var visibility: LiveData<Boolean>
): UiModel