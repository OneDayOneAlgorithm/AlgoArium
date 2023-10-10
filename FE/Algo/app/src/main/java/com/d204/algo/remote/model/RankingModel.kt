package com.d204.algo.remote.model

import com.squareup.moshi.Json

data class RankingModel(
    @Json(name = "userRankingId") val id: Long,
    @Json(name = "userId") val userId: Long,
    @Json(name = "score") val score: Int,
    @Json(name = "tier") val tier: Int,
    @Json(name = "ranking") val ranking: Int,
    @Json(name = "kakaoNickname") val kakaoNickname: String,
    @Json(name = "profileImage") val profileImage: String,
)
