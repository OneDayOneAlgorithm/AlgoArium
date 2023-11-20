package com.d204.algo.data.source.cache

import com.d204.algo.data.model.Ranking
import com.d204.algo.data.repository.cache.RankingCache
import com.d204.algo.data.repository.datasource.RankingDataSource
import javax.inject.Inject

class RankingCacheDataSource @Inject constructor(
    private val userCache: RankingCache
) : RankingDataSource {
    override suspend fun getRankingTop(tier: Int): Ranking {
        throw UnsupportedOperationException("getRankings is not supported for UserCacheDataSource.")
    }

    override suspend fun getRankingsByTier(tier: Int): List<Ranking> {
        throw UnsupportedOperationException("getRankings is not supported for UserCacheDataSource.")
    }

    override suspend fun getRanking(userId: Long): Ranking {
        throw UnsupportedOperationException("getRankings is not supported for UserCacheDataSource.")
    }

    override suspend fun isRemote(): Boolean {
        throw UnsupportedOperationException("getRankings is not supported for UserCacheDataSource.")
    }
}