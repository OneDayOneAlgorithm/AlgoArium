package com.d204.algo.remote.model

import com.squareup.moshi.Json

data class UserModel(
    @Json(name = "userId") val id: Long,
    @Json(name = "kakaoId") val kakaoId: String,
    @Json(name = "kakaoNickname") val kakaoNickname: String,
    @Json(name = "profileImage") val profileImage: String,
    @Json(name = "preTier") val preTier: Int,
    @Json(name = "tier") val tier: Int,
    @Json(name = "solvedAcId") val solvedAcId: String,
)
