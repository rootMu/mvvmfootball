package com.matthew.mvvmfootball.modules.list.ui

import androidx.appcompat.app.AlertDialog

interface OnNetworkErrorSelect {
    fun errorDialog(): AlertDialog.Builder
}
