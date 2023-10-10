package com.d204.algo.data.repository.remote

import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.model.Ranking

interface RankingRemote {
    suspend fun getRankingTop(tier: Int): NetworkResult<Ranking>
    suspend fun getRankingsByTier(tier: Int): NetworkResult<List<Ranking>>
    suspend fun getRanking(userId: Long): NetworkResult<Ranking>
    suspend fun isRemote(): Boolean
}
