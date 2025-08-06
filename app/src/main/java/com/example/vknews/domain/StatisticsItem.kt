package com.example.vknews.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticsItem(
    val type: TypeStatistics,
    val count: Int
): Parcelable

enum class TypeStatistics {
    VIEWS, LIKES, COMMENTS, REPOSTS
}