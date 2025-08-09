package com.example.vknews.presentation.postScreen

import com.example.vknews.domain.DataPostCard

sealed class PostsState {
    object Initial: PostsState()

    object Loading: PostsState()

    data class Posts(
        val posts: List<DataPostCard>,
        val isLoading: Boolean = false
    ): PostsState()
}