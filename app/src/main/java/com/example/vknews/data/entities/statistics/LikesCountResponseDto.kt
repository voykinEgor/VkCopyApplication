package com.example.vknews.data.entities.statistics

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likesCount: LikesCountDto
)
