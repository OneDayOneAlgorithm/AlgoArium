package com.d204.algo.data.source.remote

import com.d204.algo.data.api.successOr
import com.d204.algo.data.model.Problem
import com.d204.algo.data.model.Status
import com.d204.algo.data.repository.datasource.StatusDataSource
import com.d204.algo.data.repository.remote.StatusRemote
import javax.inject.Inject

class StatusRemoteDataSource @Inject constructor(
    private val statusRemote: StatusRemote,
) : StatusDataSource {
    override suspend fun getStatus(userId: Long): Status {
        return statusRemote.getStatus(userId).successOr(Status())
    }

    override suspend fun getAvgStatus(tier: Int): Status {
        return statusRemote.getAvgStatus(tier).successOr(Status())
    }

    override suspend fun updateMemo(problem: Problem) {
        statusRemote.updateMemo(problem)
    }

    override suspend fun isRemote(): Boolean {
        return statusRemote.isRemote()
    }
}
