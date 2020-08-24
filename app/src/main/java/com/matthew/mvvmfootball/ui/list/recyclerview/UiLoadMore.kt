package com.matthew.mvvmfootball.ui.list.recyclerview

import androidx.lifecycle.LiveData

data class UiLoadMore(
    override var name: String = "",
    var onClick: () -> Unit,
    var visibility: LiveData<Boolean>
) :
    UiModel