package com.d204.algo.data.source.datasource

import com.d204.algo.data.repository.datasource.ProblemDataSource
import com.d204.algo.data.source.cache.ProblemCacheDataSource
import com.d204.algo.data.source.remote.ProblemRemoteDataSource
import javax.inject.Inject

open class ProblemDataSourceFactory @Inject constructor(
    private val cacheDataSource: ProblemCacheDataSource,
    private val remoteDataSource: ProblemRemoteDataSource,
) {
    open suspend fun getDataSource(isRemote: Boolean): ProblemDataSource {
        return if (isRemote) {
            getRemoteDataSource()
        } else {
            getCacheDataSource()
        }
    }

    fun getRemoteDataSource(): ProblemDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): ProblemDataSource {
        return cacheDataSource
    }
}
