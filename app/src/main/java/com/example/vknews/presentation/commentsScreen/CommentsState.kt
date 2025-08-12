package com.example.vknews.presentation.commentsScreen

import com.example.vknews.domain.entities.CommentItem
import com.example.vknews.domain.entities.DataPostCard

sealed class CommentsState {
    object Initial: CommentsState()
    data class Comments(val post: DataPostCard, val comments: List<CommentItem>): CommentsState()
}