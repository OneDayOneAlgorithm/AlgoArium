package com.d204.algo.remote.model

import com.squareup.moshi.Json

data class StatusModel(
//    @Json(name = "statusId") val id: Long,
    @Json(name = "userId") val userId: Long,
    @Json(name = "userStatus1") val wisdom: Int,
    @Json(name = "userStatus2") val vitality: Int,
    @Json(name = "userStatus3") val strength: Int,
    @Json(name = "userStatus4") val charisma: Int,
    @Json(name = "userStatus5") val luck: Int,
)
