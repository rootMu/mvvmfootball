package com.matthew.mvvmfootball.ui.list.recyclerview

data class UiTitle(override var name: String = "", var onClick: () -> Unit) : UiModel