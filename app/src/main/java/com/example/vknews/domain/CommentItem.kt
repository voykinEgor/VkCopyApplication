package com.example.vknews.domain

data class CommentItem(
    val id: Int,
    val authorName: String = "Author $id",
    val commentText: String = "Long text comment",
    val publicationTime: String = "14:00"
)
