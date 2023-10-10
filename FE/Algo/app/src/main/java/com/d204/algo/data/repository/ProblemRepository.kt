package com.d204.algo.data.repository

import com.d204.algo.data.model.Problem
import kotlinx.coroutines.flow.Flow

interface ProblemRepository {
    suspend fun getProblems(): Flow<List<Problem>>
    suspend fun getProblem(problemId: Long): Flow<Problem>
    suspend fun getStrongProblems(userId: Long): Flow<List<Problem>>
    suspend fun getWeakProblems(userId: Long): Flow<List<Problem>>
    suspend fun getSimilarProblems(userId: Long): Flow<List<Problem>>
    suspend fun postLikeProblems(problem: Problem): Flow<Unit>
    suspend fun getLikeProblems(userId: Long): Flow<List<Problem>>
}
