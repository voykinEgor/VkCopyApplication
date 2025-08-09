package com.example.vknews.data.entities

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val response: CommentsAndProfilesListDto
)
