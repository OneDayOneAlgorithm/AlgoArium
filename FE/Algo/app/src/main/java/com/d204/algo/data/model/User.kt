package com.d204.algo.data.model

import android.os.Parcelable
import com.d204.algo.ui.adapter.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    override val id: Long,
    val kakaoId: String,
    val kakaoNickname: String,
    var profileImage: String,
    var preTier: Int,
    var tier: Int,
    var solvedAcId: String,
) : Parcelable, Identifiable {
    constructor() : this(0, "", "", "", 0, 0, "")
}
