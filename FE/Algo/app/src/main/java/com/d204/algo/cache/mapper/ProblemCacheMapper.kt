package com.d204.algo.cache.mapper

import com.d204.algo.cache.model.ProblemCacheModel
import com.d204.algo.data.model.Problem
import javax.inject.Inject

class ProblemCacheMapper @Inject constructor() : CacheMapper<ProblemCacheModel, Problem> {
    override fun mapFromCached(type: ProblemCacheModel): Problem {
        TODO("Not yet implemented")
    }

    override fun mapToCached(type: Problem): ProblemCacheModel {
        TODO("Not yet implemented")
    }
}
