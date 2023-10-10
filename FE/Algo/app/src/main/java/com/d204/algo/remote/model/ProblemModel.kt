package com.d204.algo.remote.model

import com.squareup.moshi.Json

data class ProblemModel(
    @Json(name = "problemId") val id: Long,
    @Json(name = "problemNumber") val problemNumber: Int,
    @Json(name = "title") val title: String,
    @Json(name = "problemTag") val problemTag: String,
    @Json(name = "problemLevel") val problemLevel: Int,
    @Json(name = "solvedUserCount") val solvedUserCount: Int,
    @Json(name = "likeType") val problemLike: Boolean,
    @Json(name = "memo") val problemMemo: String?,
    @Json(name = "userId") val userId: Long,
)
