package com.example.vknews.presentation.postScreen

import com.example.vknews.domain.DataPostCard

sealed class PostsState {
    object Initial: PostsState()

    data class Posts(val posts: List<DataPostCard>): PostsState()
}