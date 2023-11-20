package com.d204.algo.cache

import com.d204.algo.cache.mapper.UserCacheMapper
import com.d204.algo.data.repository.cache.UserCache
import javax.inject.Inject

class UserCacheImpl @Inject constructor(
    // private val userDao: UserDao,
    private val userCacheMapper: UserCacheMapper,
) : UserCache
