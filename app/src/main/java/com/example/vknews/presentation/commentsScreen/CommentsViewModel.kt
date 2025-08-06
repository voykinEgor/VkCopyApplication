package com.example.vknews.presentation.commentsScreen

import androidx.lifecycle.ViewModel
import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommentsViewModel(
    postCard: DataPostCard
): ViewModel() {

    private val _commentsState = MutableStateFlow<CommentsState>(CommentsState.Initial)
    val commentsState = _commentsState.asStateFlow()

    init {
        loadComments(postCard)
    }

    fun loadComments(postCard: DataPostCard){
        val commentsList = mutableListOf<CommentItem>().apply {
            repeat(10){
                add(CommentItem(id = it))
            }
        }
        _commentsState.value = CommentsState.Comments(postCard, commentsList)
    }

}