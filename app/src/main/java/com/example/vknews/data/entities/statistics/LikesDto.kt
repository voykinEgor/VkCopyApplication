package com.example.vknews.data.entities.statistics

import com.google.gson.annotations.SerializedName


data class LikesDto(
    @SerializedName("count") val count: Int,
    @SerializedName("user_likes") val isUserLikes: Int
)
