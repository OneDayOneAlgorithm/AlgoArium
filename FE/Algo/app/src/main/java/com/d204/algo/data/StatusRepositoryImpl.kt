package com.d204.algo.data

import com.d204.algo.data.model.Problem
import com.d204.algo.data.model.Status
import com.d204.algo.data.repository.StatusRepository
import com.d204.algo.data.source.datasource.StatusDataSourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatusRepositoryImpl @Inject constructor(
    private val dataSourceFactory: StatusDataSourceFactory,
) : StatusRepository {
    override suspend fun getStatus(userId: Long): Flow<Status> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 StatusRemoteImpl에 구현돼있음
        emit(dataSourceFactory.getDataStore(isRemote).getStatus(userId))
    }

    override suspend fun getAvgStatus(tier: Int): Flow<Status> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 StatusRemoteImpl에 구현돼있음
        emit(dataSourceFactory.getDataStore(isRemote).getAvgStatus(tier))
    }

    override suspend fun updateMemo(problem: Problem): Flow<Unit> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 StatusRemoteImpl에 구현돼있음
        dataSourceFactory.getDataStore(isRemote).updateMemo(problem)
    }
}
