package com.matthew.mvvmfootball.modules.list.ui

import androidx.lifecycle.LiveData

data class UiClub(
    override var name: String = "",
    var nationality: String = "",
    var homeGround: String = "",
    var city: String = "",
    var visibility: LiveData<Boolean>
):ListUiModel