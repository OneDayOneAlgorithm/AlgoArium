package com.d204.algo.cache.mapper

import com.d204.algo.cache.model.StatusCacheModel
import com.d204.algo.data.model.Status
import javax.inject.Inject

class StatusCacheMapper @Inject constructor() : CacheMapper<StatusCacheModel, Status> {
    override fun mapFromCached(type: StatusCacheModel): Status {
        TODO("Not yet implemented")
    }

    override fun mapToCached(type: Status): StatusCacheModel {
        TODO("Not yet implemented")
    }
}