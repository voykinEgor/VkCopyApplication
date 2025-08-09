package com.example.vknews.presentation.commentsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.FeedPostRepository
import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentsViewModel(
    postCard: DataPostCard
): ViewModel() {
    val repository = FeedPostRepository()
    private val _commentsState = MutableStateFlow<CommentsState>(CommentsState.Initial)
    val commentsState = _commentsState.asStateFlow()

    init {
        loadComments(postCard)
    }

    fun loadComments(postCard: DataPostCard){
        viewModelScope.launch {
            val commentsList = repository.getComments(postCard)
            _commentsState.value = CommentsState.Comments(postCard, commentsList)
        }
    }

}