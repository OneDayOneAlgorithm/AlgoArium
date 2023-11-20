package com.d204.algo.data

import com.d204.algo.data.model.User
import com.d204.algo.data.repository.UserRepository
import com.d204.algo.data.source.datasource.UserDataSourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSourceFactory: UserDataSourceFactory,
    // private val userMapper: UserMapper, // DomainLayer가 있으면 사용한다 (Entity <-> DTO)
) : UserRepository {
    // local , remote 모두에서 가져올 수 있는 경우 isRemote를 이용해서 어디서 가져올 것인지 사용자에게 입력받는다.
    // remote return 타입 Response 이므로 body() 붙여야함
    // 로컬에서 올 수 있는데 Response를 어떻게 분기 처리할까?
    // Service는 Response로 return <> Repository는 NetworkResult<Response> return
    override suspend fun getUsers(): Flow<List<User>> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 UserRemoteImpl에 구현돼있음
        emit(dataSourceFactory.getDataStore(isRemote).getUsers())
    }

    override suspend fun getUsersByTier(tier: Int): Flow<List<User>> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 UserRemoteImpl에 구현돼있음
        emit(dataSourceFactory.getDataStore(isRemote).getUsersByTier(tier))
    }

    override suspend fun getUser(accessToken: String, refreshToken: String): Flow<User> = flow {
        emit(dataSourceFactory.getRemoteDataSource().getUser(accessToken, refreshToken))
    }

    override suspend fun registerSolvedAc(userId: Long, solvedAcId: String, code: String): String = dataSourceFactory.getRemoteDataSource().registerSolvedAc(userId, solvedAcId, code)
    override suspend fun getIsSeason(): Boolean? = dataSourceFactory.getRemoteDataSource().getIsSeason()
}
