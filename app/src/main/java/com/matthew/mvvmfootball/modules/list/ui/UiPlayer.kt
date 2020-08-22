package com.matthew.mvvmfootball.modules.list.ui

data class UiPlayer(override var name: String = "",
                    var age: String = "",
                    var club: String = ""): ListUiModel
