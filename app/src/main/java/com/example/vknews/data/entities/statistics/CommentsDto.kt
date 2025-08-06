package com.example.vknews.data.entities.statistics

import com.google.gson.annotations.SerializedName

data class CommentsDto(
    @SerializedName("count") val count: Int
)
