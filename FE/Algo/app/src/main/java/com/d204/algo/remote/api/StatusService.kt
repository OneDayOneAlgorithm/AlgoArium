package com.d204.algo.remote.api

import com.d204.algo.remote.model.ProblemModel
import com.d204.algo.remote.model.StatusModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StatusService {
    @GET("my/stat/{userId}")
    suspend fun getStatus(@Path("userId") userId: Long): StatusModel

    @GET("my/stat/average/{tier}")
    suspend fun getAvgStatus(@Path("tier") tier: Int): StatusModel

    @POST("api/problemLikes/updateMemo")
    suspend fun updateMemo(@Body problemModel: ProblemModel)
}
