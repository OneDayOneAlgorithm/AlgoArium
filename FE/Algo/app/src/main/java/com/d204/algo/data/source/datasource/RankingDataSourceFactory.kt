package com.d204.algo.data.source.datasource

import com.d204.algo.data.repository.datasource.RankingDataSource
import com.d204.algo.data.source.cache.RankingCacheDataSource
import com.d204.algo.data.source.remote.RankingRemoteDataSource
import javax.inject.Inject

open class RankingDataSourceFactory @Inject constructor(
    private val cacheDataSource: RankingCacheDataSource,
    private val remoteDataSource: RankingRemoteDataSource
) {
    open suspend fun getDataStore(isRemote: Boolean): RankingDataSource {
        return if (isRemote) {
            return getRemoteDataSource()
        } else {
            getCacheDataSource()
        }
    }

    fun getRemoteDataSource(): RankingDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): RankingDataSource {
        return cacheDataSource
    }
}