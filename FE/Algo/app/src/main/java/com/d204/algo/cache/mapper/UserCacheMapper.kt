package com.d204.algo.cache.mapper

import com.d204.algo.cache.model.UserCacheModel
import com.d204.algo.data.model.User
import javax.inject.Inject

class UserCacheMapper @Inject constructor() :
    CacheMapper<UserCacheModel, User> {
    override fun mapFromCached(type: UserCacheModel): User {
        TODO("Not yet implemented")
    }

    override fun mapToCached(type: User): UserCacheModel {
        TODO("Not yet implemented")
    }
}