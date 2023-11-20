package com.d204.algo.remote

import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.api.handleApi
import com.d204.algo.data.model.Ranking
import com.d204.algo.data.repository.remote.RankingRemote
import com.d204.algo.remote.api.RankingService
import com.d204.algo.remote.mapper.RankingMapper
import javax.inject.Inject

class RankingRemoteImpl @Inject constructor(
    private val rankingService: RankingService,
    private val rankingMapper: RankingMapper,
) : RankingRemote {
    override suspend fun getRankingTop(tier: Int): NetworkResult<Ranking> {
        return handleApi {
            rankingMapper.mapFromModel(
                rankingService.getRankingTop(tier),
            )
        }
    }

    override suspend fun getRankingsByTier(tier: Int): NetworkResult<List<Ranking>> {
        return handleApi {
            rankingService.getRankingsByTier(tier).map { rankingModel ->
                rankingMapper.mapFromModel(rankingModel)
            }
        }
    }

    override suspend fun getRanking(userId: Long): NetworkResult<Ranking> {
        return handleApi {
            rankingMapper.mapFromModel(rankingService.getRanking(userId))
        }
    }

    override suspend fun isRemote(): Boolean {
        return true
    }
}
