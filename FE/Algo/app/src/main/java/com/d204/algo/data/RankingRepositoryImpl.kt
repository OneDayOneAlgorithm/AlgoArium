package com.d204.algo.data

import com.d204.algo.data.model.Ranking
import com.d204.algo.data.repository.RankingRepository
import com.d204.algo.data.source.datasource.RankingDataSourceFactory
import com.d204.algo.remote.mapper.RankingMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val dataSourceFactory: RankingDataSourceFactory,
    private val rankingMapper: RankingMapper,
) : RankingRepository {
    override suspend fun getRankingTop(tier: Int): Flow<Ranking> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 UserRemoteImpl에 구현돼있음
        emit(dataSourceFactory.getDataStore(isRemote).getRankingTop(tier))
    }

    override suspend fun getRankingsByTier(tier: Int): Flow<List<Ranking>> = flow {
        val isRemote = dataSourceFactory.getRemoteDataSource().isRemote() // 무조건 true가 나오도록 UserRemoteImpl에 구현돼있음
        emit(dataSourceFactory.getDataStore(isRemote).getRankingsByTier(tier))
    }

    override suspend fun getRanking(userId: Long): Flow<Ranking> = flow {
        emit(dataSourceFactory.getRemoteDataSource().getRanking(userId))
    }
}
