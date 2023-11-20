package com.d204.algo.data.model

import android.os.Parcelable
import com.d204.algo.ui.adapter.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    override val id: Long,
    val userId: Long,
    var wisdom: Int,
    var vitality: Int,
    var strength: Int,
    var charisma: Int,
    var luck: Int
) : Parcelable, Identifiable {
    constructor() : this(
        -1L,
        -1L,
        -1,
        -1,
        -1,
        -1,
        -1,
    )
}
