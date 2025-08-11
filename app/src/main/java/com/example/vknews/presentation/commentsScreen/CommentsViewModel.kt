package com.example.vknews.presentation.commentsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.FeedPostRepository
import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CommentsViewModel(
    postCard: DataPostCard
): ViewModel() {
    val repository = FeedPostRepository()
    val commentsState = repository.getComments(postCard)
        .filter { it.isNotEmpty() }
        .onStart { CommentsState.Initial }
        .map { CommentsState.Comments(postCard, it) }

}