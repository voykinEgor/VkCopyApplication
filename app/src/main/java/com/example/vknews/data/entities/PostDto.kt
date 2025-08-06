package com.example.vknews.data.entities

import com.example.vknews.data.entities.statistics.CommentsDto
import com.example.vknews.data.entities.statistics.LikesDto
import com.example.vknews.data.entities.statistics.RepostsDto
import com.example.vknews.data.entities.statistics.ViewsDto
import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("is_favorite") val like: Boolean,
    @SerializedName("text") val text: String,
    @SerializedName("likes") val countLikes: LikesDto,
    @SerializedName("reposts") val countReposts: RepostsDto,
    @SerializedName("views") val countViews: ViewsDto,
    @SerializedName("comments") val countComments: CommentsDto
)
