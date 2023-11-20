package com.d204.algo.data.repository

import com.d204.algo.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<List<User>>
    suspend fun getUsersByTier(tier: Int): Flow<List<User>>
    suspend fun getUser(accessToken: String, refreshToken: String): Flow<User>
    suspend fun registerSolvedAc(userId: Long, solvedAcId: String, code: String): String
    suspend fun getIsSeason(): Boolean?
}
