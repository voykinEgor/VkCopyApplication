package com.example.vknews.data.entities

import com.google.gson.annotations.SerializedName

data class ItemsDto(
    @SerializedName("items") val posts: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDto>
)
