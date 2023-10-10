package com.d204.algo.data.source.datasource

import com.d204.algo.data.repository.datasource.UserDataSource
import com.d204.algo.data.source.cache.UserCacheDataSource
import com.d204.algo.data.source.remote.UserRemoteDataSource
import javax.inject.Inject

// UserDataSource 인터페이스 Impl을 찍어내는 Factory
open class UserDataSourceFactory @Inject constructor(
    private val cacheDataSource: UserCacheDataSource,
    private val remoteDataSource: UserRemoteDataSource
) {
    open suspend fun getDataStore(isRemote: Boolean): UserDataSource {
        return if (isRemote) {
            return getRemoteDataSource()
        } else {
            getCacheDataSource()
        }
    }

    fun getRemoteDataSource(): UserDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): UserDataSource {
        return cacheDataSource
    }
}