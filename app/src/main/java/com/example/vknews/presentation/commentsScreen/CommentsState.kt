package com.example.vknews.presentation.commentsScreen

import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard

sealed class CommentsState {
    object Initial: CommentsState()
    data class Comments(val post: DataPostCard, val comments: List<CommentItem>): CommentsState()
}