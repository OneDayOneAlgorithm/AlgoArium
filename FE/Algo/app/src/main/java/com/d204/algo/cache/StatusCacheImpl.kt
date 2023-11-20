package com.d204.algo.cache

import com.d204.algo.cache.mapper.StatusCacheMapper
import com.d204.algo.data.repository.cache.StatusCache
import javax.inject.Inject

class StatusCacheImpl @Inject constructor(
//    private val rankingDao: RankingDao,
    private val statusCacheMapper: StatusCacheMapper,
) : StatusCache
