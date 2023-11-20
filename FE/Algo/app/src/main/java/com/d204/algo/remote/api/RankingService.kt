package com.d204.algo.remote.api

import com.d204.algo.remote.model.RankingModel
import retrofit2.http.GET
import retrofit2.http.Path

interface RankingService {
    @GET("rank/top/{tier}")
    suspend fun getRankingTop(@Path("tier") tier: Int): RankingModel

    @GET("rank/{tier}")
    suspend fun getRankingsByTier(@Path("tier") tier: Int): List<RankingModel>

    @GET("rank/my/{userId}")
    suspend fun getRanking(@Path("id") id: Long): RankingModel
}
