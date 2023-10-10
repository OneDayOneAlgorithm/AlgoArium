package com.d204.algo.remote.model

import com.squareup.moshi.Json

data class StatusModel(
    @field:Json(name = "statusId") val id: Long,
    @field:Json(name = "userId") val userId: Long,
    @field:Json(name = "wisdom") val wisdom: Int,
    @field:Json(name = "vitality") val vitality: Int,
    @field:Json(name = "strength") val strength: Int,
    @field:Json(name = "charisma") val charisma: Int,
    @field:Json(name = "luck") val luck: Int,
)
