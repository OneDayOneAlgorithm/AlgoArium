package com.d204.algo.data.source.remote

import android.util.Log
import com.d204.algo.data.api.successOr
import com.d204.algo.data.model.Problem
import com.d204.algo.data.repository.datasource.ProblemDataSource
import com.d204.algo.data.repository.remote.ProblemRemote
import javax.inject.Inject

class ProblemRemoteDataSource @Inject constructor(
    private val problemRemote: ProblemRemote,
) : ProblemDataSource {
    override suspend fun getProblems(): List<Problem> {
        return problemRemote.getProblems().successOr(emptyList())
    }

    override suspend fun getProblem(problemId: Long): Problem {
        return problemRemote.getProblem(problemId).successOr(Problem())
    }

    override suspend fun getStrongProblems(userId: Long): List<Problem> {
        return problemRemote.getStrongProblems(userId).successOr(emptyList())
    }

    override suspend fun getWeakProblems(userId: Long): List<Problem> {
        return problemRemote.getWeakProblems(userId).successOr(emptyList())
    }

    override suspend fun getSimilarProblems(userId: Long): List<Problem> {
        return problemRemote.getSimilarProblems(userId)
    }

    override suspend fun postLikeProblems(problem: Problem) {
        Log.d("클릭데이터소스", "postLikeProblems: $problem")
        return problemRemote.postLikeProblems(problem).successOr(Unit)
    }

    override suspend fun getLikeProblems(userId: Long): List<Problem> {
        return problemRemote.getLikeProblems(userId)
    }

    override suspend fun isRemote(): Boolean {
        return problemRemote.isRemote()
    }
}
