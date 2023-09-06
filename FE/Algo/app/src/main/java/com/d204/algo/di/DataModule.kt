package com.d204.algo.di

import com.d204.algo.data.UserRepositoryImpl
import com.d204.algo.data.repository.RankingRepository
import com.d204.algo.data.RankingRepositoryImpl
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
}
