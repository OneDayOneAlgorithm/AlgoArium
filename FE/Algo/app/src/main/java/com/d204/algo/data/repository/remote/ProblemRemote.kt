package com.d204.algo.data.repository.remote

import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.model.Problem

interface ProblemRemote {
    suspend fun getProblems(): NetworkResult<List<Problem>>
    suspend fun getProblem(problemId: Long): NetworkResult<Problem>
    suspend fun getStrongProblems(userId: Long): NetworkResult<List<Problem>>
    suspend fun getWeakProblems(userId: Long): NetworkResult<List<Problem>>
    suspend fun getSimilarProblems(userId: Long): List<Problem>
    suspend fun postLikeProblems(problem: Problem): NetworkResult<Unit>
    suspend fun getLikeProblems(userId: Long): List<Problem>
    suspend fun isRemote(): Boolean
}
