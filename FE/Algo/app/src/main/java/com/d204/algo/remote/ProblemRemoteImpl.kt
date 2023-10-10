package com.d204.algo.remote

import android.util.Log
import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.api.handleApi
import com.d204.algo.data.model.Problem
import com.d204.algo.data.repository.remote.ProblemRemote
import com.d204.algo.remote.api.ProblemService
import com.d204.algo.remote.mapper.ProblemMapper
import javax.inject.Inject

class ProblemRemoteImpl @Inject constructor(
    private val problemService: ProblemService,
    private val problemMapper: ProblemMapper,
) : ProblemRemote {
    override suspend fun getProblems(): NetworkResult<List<Problem>> {
        return handleApi {
            problemService.getProblems().map { problemModel ->
                problemMapper.mapFromModel(problemModel)
            }
        }
    }

    override suspend fun getProblem(problemId: Long): NetworkResult<Problem> {
        return handleApi {
            problemMapper.mapFromModel(problemService.getProblem(problemId))
        }
    }

    override suspend fun getStrongProblems(userId: Long): NetworkResult<List<Problem>> {
        return handleApi {
            problemService.getStrongProblems(userId).map { problemModel ->
                problemMapper.mapFromModel(problemModel)
            }
        }
    }

    override suspend fun getWeakProblems(userId: Long): NetworkResult<List<Problem>> {
        return handleApi {
            problemService.getWeakProblems(userId).map { problemModel ->
                problemMapper.mapFromModel(problemModel)
            }
        }
    }

    override suspend fun getSimilarProblems(userId: Long): List<Problem> {
        return problemService.getSimilarProblems(userId).map { problemModel ->
            problemMapper.mapFromModel(problemModel)
        }
    }

    override suspend fun postLikeProblems(problem: Problem): NetworkResult<Unit> {
        return handleApi {
            problemService.postLikeProblems(problemMapper.mapToModel(problem))
        }
    }

    override suspend fun getLikeProblems(userId: Long): List<Problem> {
        return problemService.getLikeProblems(userId).map { problemModel ->
            Log.d("리모트라이크프로블럼", "getLikeProblems: $problemModel")
            problemMapper.mapFromModel(problemModel)
        }
    }

    override suspend fun isRemote(): Boolean {
        return true
    }
}
