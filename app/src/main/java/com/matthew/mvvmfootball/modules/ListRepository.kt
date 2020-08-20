package com.matthew.mvvmfootball.modules

import com.matthew.mvvmfootball.room.FootballDataHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(private val footballDataHandler: FootballDataHandler) {

    fun retriveDataFromServer(searchString: String) =
        footballDataHandler.retrieveDataFromServer(searchString)

}