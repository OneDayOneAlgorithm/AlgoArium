package com.d204.algo.data.source.cache

import com.d204.algo.data.model.Problem
import com.d204.algo.data.repository.cache.ProblemCache
import com.d204.algo.data.repository.datasource.ProblemDataSource
import javax.inject.Inject

class ProblemCacheDataSource @Inject constructor(
    private val problemCache: ProblemCache
) : ProblemDataSource {
    override suspend fun getProblems(): List<Problem> {
        throw UnsupportedOperationException("getProblems is not supported for ProblemCacheDataSource.")
    }

    override suspend fun getProblem(problemId: Long): Problem {
        throw UnsupportedOperationException("getProblem is not supported for ProblemCacheDataSource.")
    }

    override suspend fun getStrongProblems(userId: Long): List<Problem> {
        throw UnsupportedOperationException("getStrongProblems is not supported for ProblemCacheDataSource.")
    }

    override suspend fun getWeakProblems(userId: Long): List<Problem> {
        throw UnsupportedOperationException("getWeakProblems is not supported for ProblemCacheDataSource.")
    }

    override suspend fun getSimilarProblems(userId: Long): List<Problem> {
        throw UnsupportedOperationException("getSimilarProblems is not supported for ProblemCacheDataSource.")
    }

    override suspend fun postLikeProblems(problem: Problem) {
        throw UnsupportedOperationException("postLikeProblems is not supported for ProblemCacheDataSource.")
    }

    override suspend fun getLikeProblems(userId: Long): List<Problem> {
        throw UnsupportedOperationException("getLikeProblems is not supported for ProblemCacheDataSource.")
    }

    override suspend fun isRemote(): Boolean {
        throw UnsupportedOperationException("isRemote is not supported for ProblemCacheDataSource.")
    }
}
