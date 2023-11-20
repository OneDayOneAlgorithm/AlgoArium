package com.d204.algo.data.repository.datasource

import com.d204.algo.data.model.Ranking

interface RankingDataSource {
    // remote
    suspend fun getRankingTop(tier: Int): Ranking
    suspend fun getRankingsByTier(tier: Int): List<Ranking>
    suspend fun getRanking(userId: Long): Ranking

    // cache

    // remote & cache (공통)

    // datasource
    suspend fun isRemote(): Boolean
}