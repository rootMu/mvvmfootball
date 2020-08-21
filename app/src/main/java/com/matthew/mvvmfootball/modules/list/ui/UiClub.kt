package com.matthew.mvvmfootball.modules.list.ui

data class UiClub(
    override var name: String = "",
    var nationality: String = "",
    var homeGround: String = "",
    var city: String = ""
):ListUiModel