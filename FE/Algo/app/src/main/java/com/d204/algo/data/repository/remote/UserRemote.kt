package com.d204.algo.data.repository.remote

import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.model.User

interface UserRemote {
    suspend fun getUsers(): NetworkResult<List<User>>
    suspend fun getUsersByTier(tier: Int): NetworkResult<List<User>>
    suspend fun getUser(accessToken: String, refreshToken: String): NetworkResult<User>
    suspend fun registerSolvedAc(userId: Long, solvedAcId: String, code: String): NetworkResult<String>
    suspend fun getIsSeason(): NetworkResult<Boolean?>
    suspend fun isRemote(): Boolean
}
