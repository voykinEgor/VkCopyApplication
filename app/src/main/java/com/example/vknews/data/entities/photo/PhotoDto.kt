package com.example.vknews.data.entities.photo

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val photos: List<PhotoUrlDto>
)
