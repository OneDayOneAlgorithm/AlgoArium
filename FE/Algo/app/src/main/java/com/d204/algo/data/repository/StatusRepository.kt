package com.d204.algo.data.repository

import com.d204.algo.data.model.Problem
import com.d204.algo.data.model.Status
import kotlinx.coroutines.flow.Flow

interface StatusRepository {
    suspend fun getStatus(userId: Long): Flow<Status>
    suspend fun getAvgStatus(tier: Int): Flow<Status>
    suspend fun updateMemo(problem: Problem): Flow<Unit>
}
