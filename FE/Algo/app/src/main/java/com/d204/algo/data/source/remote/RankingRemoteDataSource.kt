package com.d204.algo.data.source.remote

import android.util.Log
import com.d204.algo.data.api.successOr
import com.d204.algo.data.model.Ranking
import com.d204.algo.data.repository.datasource.RankingDataSource
import com.d204.algo.data.repository.remote.RankingRemote
import javax.inject.Inject

class RankingRemoteDataSource @Inject constructor(
    private val rankingRemote: RankingRemote,
) : RankingDataSource {
    override suspend fun getRankingTop(tier: Int): Ranking {
        return rankingRemote.getRankingTop(tier).successOr(Ranking())
    }

    override suspend fun getRankingsByTier(tier: Int): List<Ranking> {
        return rankingRemote.getRankingsByTier(tier).successOr(emptyList())
    }

    override suspend fun getRanking(userId: Long): Ranking {
        return rankingRemote.getRanking(userId).successOr(Ranking())
    }

    override suspend fun isRemote(): Boolean {
        return rankingRemote.isRemote()
    }
}
