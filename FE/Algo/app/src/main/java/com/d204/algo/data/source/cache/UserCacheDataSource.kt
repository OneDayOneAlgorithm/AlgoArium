package com.d204.algo.data.source.cache

import com.d204.algo.data.model.User
import com.d204.algo.data.repository.cache.UserCache
import com.d204.algo.data.repository.datasource.UserDataSource
import javax.inject.Inject

class UserCacheDataSource @Inject constructor(
    private val userCache: UserCache
) : UserDataSource {
    override suspend fun getUsers(): List<User> {
        throw UnsupportedOperationException("getUsers is not supported for UserCacheDataSource.")
    }

    override suspend fun getUsersByTier(tier: Int): List<User> {
        throw UnsupportedOperationException("getUser is not supported for UserCacheDataSource.")
    }

    override suspend fun getUser(accessToken: String, refreshToken: String): User {
        throw UnsupportedOperationException("getUser is not supported for UserCacheDataSource.")
    }

    override suspend fun registerSolvedAc(userId: Long, solvedAcId: String, code: String): String {
        throw UnsupportedOperationException("registerSolvedAc is not supported for UserCacheDataSource.")
    }

    override suspend fun getIsSeason(): Boolean? {
        throw UnsupportedOperationException("getIsSeason is not supported for UserCacheDataSource.")
    }

    override suspend fun isRemote(): Boolean {
        throw UnsupportedOperationException("isRemote is not supported for UserCacheDataSource.")
    }
}