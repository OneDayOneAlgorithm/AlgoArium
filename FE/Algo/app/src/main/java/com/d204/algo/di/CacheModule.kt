package com.d204.algo.di

import com.d204.algo.cache.ProblemCacheImpl
import com.d204.algo.cache.RankingCacheImpl
import com.d204.algo.cache.StatusCacheImpl
import com.d204.algo.cache.UserCacheImpl
import com.d204.algo.data.repository.cache.ProblemCache
import com.d204.algo.data.repository.cache.RankingCache
import com.d204.algo.data.repository.cache.StatusCache
import com.d204.algo.data.repository.cache.UserCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideUserCache(userCache: UserCacheImpl): UserCache {
        return userCache
    }

    @Provides
    @Singleton
    fun provideRankingCache(rankingCache: RankingCacheImpl): RankingCache {
        return rankingCache
    }

    @Provides
    @Singleton
    fun provideStatusCache(statusCache: StatusCacheImpl): StatusCache {
        return statusCache
    }

    @Provides
    @Singleton
    fun provideProblemCache(problemCache: ProblemCacheImpl): ProblemCache {
        return problemCache
    }
//
//    @Provides
//    @Singleton
//    fun provideRoomDatabase(@ApplicationContext context: Context): CharactersDatabase {
//        return CharactersDatabase.getInstance(context)
//    }
//
//    @Provides
//    @Singleton
//    fun provideCharacterDao(charactersDatabase: CharactersDatabase): CharacterDao {
//        return charactersDatabase.cachedCharacterDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideCharacterCache(characterCache: CharacterCacheImp): CharacterCache {
//        return characterCache
//    }
//
//    @Provides
//    @Singleton
//    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
//        return CachePreferencesHelper(context)
//    }
}
