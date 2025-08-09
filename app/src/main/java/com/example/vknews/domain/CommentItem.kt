package com.example.vknews.domain

data class CommentItem(
    val id: Long,
    val authorName: String,
    val authorImageUrl: String,
    val commentText: String,
    val publicationTime: String
)
