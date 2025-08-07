package com.example.vknews.data.entities

import com.google.gson.annotations.SerializedName

data class ResponseFeedPost(
    @SerializedName("response") val response: ItemsDto
)
