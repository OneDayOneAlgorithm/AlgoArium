package com.d204.algo.remote

import android.util.Log
import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.api.handleApi
import com.d204.algo.data.model.Problem
import com.d204.algo.data.model.Status
import com.d204.algo.data.repository.remote.StatusRemote
import com.d204.algo.remote.api.StatusService
import com.d204.algo.remote.mapper.ProblemMapper
import com.d204.algo.remote.mapper.StatusMapper
import javax.inject.Inject

private const val TAG = "StatusRemoteImpl"
class StatusRemoteImpl @Inject constructor(
    private val statusService: StatusService,
    private val statusMapper: StatusMapper,
    private val problemMapper: ProblemMapper,
) : StatusRemote {
    override suspend fun getStatus(userId: Long): NetworkResult<Status> {
        Log.d(TAG, "getStatus: ddd")
        val aa = handleApi {
            statusMapper.mapFromModel(statusService.getStatus(userId))
        }
        Log.d(TAG, "getStatus: $aa")
        return aa
    }

    override suspend fun getAvgStatus(tier: Int): NetworkResult<Status> {
        return handleApi {
            statusMapper.mapFromModel(statusService.getAvgStatus(tier))
        }
    }

    override suspend fun updateMemo(problem: Problem): NetworkResult<Unit> {
        return handleApi {
            statusService.updateMemo(problemMapper.mapToModel(problem))
        }
    }

    // 이 부분 원래는 로컬 DB에 저장된 값이 없으면 true를 호출하게 끔 StatusDao를 이용해야함. 현재는 DataSource에서 받아서 사용
    override suspend fun isRemote(): Boolean {
        return true
    }
}
