package com.example.vknews.presentation.commentsScreen

import androidx.lifecycle.ViewModel
import com.example.vknews.data.FeedPostRepositoryImpl
import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.domain.useCases.GetCommentsUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CommentsViewModel(
    postCard: DataPostCard
): ViewModel() {
    private val repository = FeedPostRepositoryImpl()

    private val getCommentsUseCase = GetCommentsUseCase(repository)
    val commentsState = getCommentsUseCase(postCard)
        .filter { it.isNotEmpty() }
        .onStart { CommentsState.Initial }
        .map { CommentsState.Comments(postCard, it) }

}