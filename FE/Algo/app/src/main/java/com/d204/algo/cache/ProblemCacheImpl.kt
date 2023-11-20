package com.d204.algo.cache

import com.d204.algo.cache.mapper.ProblemCacheMapper
import com.d204.algo.data.repository.cache.ProblemCache
import javax.inject.Inject

class ProblemCacheImpl @Inject constructor(
    private val problemCacheMapper: ProblemCacheMapper,
) : ProblemCache
