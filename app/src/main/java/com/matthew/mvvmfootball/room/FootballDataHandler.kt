package com.matthew.mvvmfootball.room

import androidx.annotation.NonNull
import com.matthew.mvvmfootball.dagger.qualifier.ForDatabase
import com.matthew.mvvmfootball.network.FootballApi
import okhttp3.ResponseBody
import java.util.concurrent.Executor
import javax.inject.Inject


class FootballDataHandler @Inject constructor(
    private val footballDao: FootballDao,
    private val footballApi: FootballApi,
    @ForDatabase @NonNull private val databaseExecutor: Executor
) {
    private fun handleError(exception: ResponseBody?) {}
}