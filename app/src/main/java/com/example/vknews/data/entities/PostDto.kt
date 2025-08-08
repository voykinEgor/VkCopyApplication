package com.example.vknews.data.entities

import com.example.vknews.data.entities.statistics.CommentsDto
import com.example.vknews.data.entities.statistics.LikesDto
import com.example.vknews.data.entities.statistics.RepostsDto
import com.example.vknews.data.entities.statistics.ViewsDto
import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("source_id") val ownerId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("attachments") val attachments: List<AttachmentsDto>?
)
