package com.d204.algo.data.source.datasource

import com.d204.algo.data.repository.datasource.StatusDataSource
import com.d204.algo.data.source.cache.StatusCacheDataSource
import com.d204.algo.data.source.remote.StatusRemoteDataSource
import javax.inject.Inject

// StatusDataSource 인터페이스 Impl을 찍어내는 Factory
open class StatusDataSourceFactory @Inject constructor(
    private val cacheDataSource: StatusCacheDataSource,
    private val remoteDataSource: StatusRemoteDataSource
) {
    open suspend fun getDataStore(isRemote: Boolean): StatusDataSource {
        return if (isRemote) {
            return getRemoteDataSource()
        } else {
            getCacheDataSource()
        }
    }

    fun getRemoteDataSource(): StatusDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): StatusDataSource {
        return cacheDataSource
    }
}