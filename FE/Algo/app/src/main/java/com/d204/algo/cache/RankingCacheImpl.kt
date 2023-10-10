package com.d204.algo.cache

import com.d204.algo.cache.mapper.RankingCacheMapper
import com.d204.algo.data.repository.cache.RankingCache
import javax.inject.Inject

class RankingCacheImpl @Inject constructor(
//    private val rankingDao: RankingDao,
    private val rankingCacheMapper: RankingCacheMapper,
) : RankingCache
