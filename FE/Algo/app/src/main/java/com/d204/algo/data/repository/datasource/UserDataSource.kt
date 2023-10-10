package com.d204.algo.data.repository.datasource

import com.d204.algo.data.model.User

interface UserDataSource {
    // remote
    suspend fun getUsers(): List<User>
    suspend fun getUsersByTier(tier: Int): List<User>
    suspend fun getUser(accessToken: String, refreshToken: String): User
    suspend fun registerSolvedAc(userId: Long, solvedAcId: String, code: String): String
    suspend fun getIsSeason(): Boolean?

    // cache

    // remote & cache (공통)

    // datasource
    suspend fun isRemote(): Boolean
}
