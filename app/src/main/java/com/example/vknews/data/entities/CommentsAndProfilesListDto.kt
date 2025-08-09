package com.example.vknews.data.entities

import com.google.gson.annotations.SerializedName

data class CommentsAndProfilesListDto(
    @SerializedName("items") val commentsList: List<CommentDto>,
    @SerializedName("profiles") val listProfiles: List<ProfileDto>
)