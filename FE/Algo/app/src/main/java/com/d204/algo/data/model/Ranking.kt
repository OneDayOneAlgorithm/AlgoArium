package com.d204.algo.data.model

import android.os.Parcelable
import com.d204.algo.ui.adapter.Identifiable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ranking(
    override val id: Long,
    var userId: Long,
    var score: Int,
    var tier: Int,
    var ranking: Int,
    var kakaoNickname: String,
    var profileImage: String,
) : Parcelable, Identifiable {
    constructor() : this(0, 0, 0, 0, 0, "", "")
}
