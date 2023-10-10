package com.d204.algo.data.source.cache

import com.d204.algo.data.model.Problem
import com.d204.algo.data.model.Status
import com.d204.algo.data.repository.cache.StatusCache
import com.d204.algo.data.repository.datasource.StatusDataSource
import javax.inject.Inject

class StatusCacheDataSource @Inject constructor(
    private val StatusCache: StatusCache,
) : StatusDataSource {
    override suspend fun getStatus(userId: Long): Status {
        throw UnsupportedOperationException("getStatuss is not supported for StatusCacheDataSource.")
    }

    override suspend fun getAvgStatus(tier: Int): Status {
        throw UnsupportedOperationException("getAvgStatus is not supported for StatusCacheDataSource.")
    }

    override suspend fun updateMemo(problem: Problem) {
        throw UnsupportedOperationException("updateMemo is not supported for StatusCacheDataSource.")
    }

    override suspend fun isRemote(): Boolean {
        throw UnsupportedOperationException("isRemote is not supported for StatusCacheDataSource.")
    }
}
