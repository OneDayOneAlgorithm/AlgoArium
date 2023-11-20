package com.d204.algo.data.repository.datasource

import com.d204.algo.data.model.Problem

interface ProblemDataSource {
    // remote
    suspend fun getProblems(): List<Problem>
    suspend fun getProblem(problemId: Long): Problem
    suspend fun getStrongProblems(userId: Long): List<Problem>
    suspend fun getWeakProblems(userId: Long): List<Problem>
    suspend fun getSimilarProblems(userId: Long): List<Problem>
    suspend fun postLikeProblems(problem: Problem)
    suspend fun getLikeProblems(userId: Long): List<Problem>

    // cache

    // remote & cache (공통)

    // datasource
    suspend fun isRemote(): Boolean
}
