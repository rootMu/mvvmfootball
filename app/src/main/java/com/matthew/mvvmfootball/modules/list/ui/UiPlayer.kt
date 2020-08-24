package com.matthew.mvvmfootball.modules.list.ui

import androidx.lifecycle.LiveData

data class UiPlayer(override var name: String = "",
                    var age: String = "",
                    var club: String = "",
                    var visibility: LiveData<Boolean>
): ListUiModel
