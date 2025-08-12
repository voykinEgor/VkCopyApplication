package com.example.vknews.domain.repository

import com.example.vknews.domain.entities.CommentItem
import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.presentation.authScreen.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FeedPostRepository {

    fun getAuthState(): StateFlow<AuthState>

    fun getPosts(): StateFlow<List<DataPostCard>>

    fun getComments(feedPost: DataPostCard): StateFlow<List<CommentItem>>

    suspend fun updatePosts()

    suspend fun updateAuthState()

    suspend fun changeLikeStatus(feedPost: DataPostCard)

    suspend fun ignoreItem(feedPost: DataPostCard)


}