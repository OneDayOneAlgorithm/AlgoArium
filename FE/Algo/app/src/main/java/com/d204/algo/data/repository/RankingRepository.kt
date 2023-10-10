package com.d204.algo.data.repository

import com.d204.algo.data.model.Ranking
import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    suspend fun getRankingTop(tier: Int): Flow<Ranking>
    suspend fun getRankingsByTier(tier: Int): Flow<List<Ranking>>
    suspend fun getRanking(userId: Long): Flow<Ranking>
}
