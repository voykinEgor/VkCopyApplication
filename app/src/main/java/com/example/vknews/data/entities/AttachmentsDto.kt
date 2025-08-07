package com.example.vknews.data.entities

import com.example.vknews.data.entities.photo.PhotoDto
import com.google.gson.annotations.SerializedName

data class AttachmentsDto (
    @SerializedName("photo") val photo: PhotoDto
)