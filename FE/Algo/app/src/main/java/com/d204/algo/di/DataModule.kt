package com.d204.algo.di

import com.d204.algo.data.ProblemRepositoryImpl
import com.d204.algo.data.RankingRepositoryImpl
import com.d204.algo.data.StatusRepositoryImpl
import com.d204.algo.data.UserRepositoryImpl
import com.d204.algo.data.repository.ProblemRepository
import com.d204.algo.data.repository.RankingRepository
import com.d204.algo.data.repository.StatusRepository
import com.d204.algo.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository = userRepository

    @Provides
    @Singleton
    fun provideRankingRepository(rankingRepository: RankingRepositoryImpl): RankingRepository = rankingRepository

    @Provides
    @Singleton
    fun provideProblemRepository(problemRepository: ProblemRepositoryImpl): ProblemRepository = problemRepository

    @Provides
    @Singleton
    fun provideStatusRepository(statusRepository: StatusRepositoryImpl): StatusRepository = statusRepository
}
