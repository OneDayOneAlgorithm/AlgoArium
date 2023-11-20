package com.d204.algo.cache.mapper

import com.d204.algo.cache.model.RankingCacheModel
import com.d204.algo.data.model.Ranking
import javax.inject.Inject

class RankingCacheMapper @Inject constructor() :
    CacheMapper<RankingCacheModel, Ranking> {
    override fun mapFromCached(type: RankingCacheModel): Ranking {
        TODO("Not yet implemented")
    }

    override fun mapToCached(type: Ranking): RankingCacheModel {
        TODO("Not yet implemented")
    }
}