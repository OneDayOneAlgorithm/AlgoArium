package com.d204.algo.remote

import android.util.Log
import com.d204.algo.data.api.NetworkResult
import com.d204.algo.data.api.handleApi
import com.d204.algo.data.model.User
import com.d204.algo.data.repository.remote.UserRemote
import com.d204.algo.remote.api.UserService
import com.d204.algo.remote.mapper.UserMapper
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val userService: UserService,
    private val userMapper: UserMapper,
) : UserRemote {
    override suspend fun getUsers(): NetworkResult<List<User>> {
        return handleApi {
            userService.getUsers().map { userModel ->
                userMapper.mapFromModel(userModel)
            }
        }
    }

    override suspend fun getUsersByTier(tier: Int): NetworkResult<List<User>> {
        return handleApi {
            userService.getUsersByTier(tier).map { userModel ->
                userMapper.mapFromModel(userModel)
            }
        }
    }

    override suspend fun getUser(accessToken: String, refreshToken: String): NetworkResult<User> {
        return handleApi {
            userMapper.mapFromModel(
                userService.getUser(accessToken, refreshToken),
            )
        }
    }

    override suspend fun registerSolvedAc(userId: Long, solvedAcId: String, code: String): NetworkResult<String> {
        return handleApi {
            userService.registerSolvedAc(userId, solvedAcId, code)
        }
    }

    override suspend fun getIsSeason(): NetworkResult<Boolean?> {
        return handleApi {
            userService.getIsSeason()
        }
    }

    // 이 부분 원래는 로컬 DB에 저장된 값이 없으면 true를 호출하게 끔 userDao를 이용해야함. 현재는 DataSource에서 받아서 사용
    override suspend fun isRemote(): Boolean {
        return true
    }
}
