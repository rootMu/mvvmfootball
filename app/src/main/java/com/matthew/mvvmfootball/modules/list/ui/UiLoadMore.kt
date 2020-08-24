package com.matthew.mvvmfootball.modules.list.ui

import androidx.lifecycle.LiveData

data class UiLoadMore(override var name: String = "", var onClick: () -> Unit, var visibility: LiveData<Boolean>): ListUiModel