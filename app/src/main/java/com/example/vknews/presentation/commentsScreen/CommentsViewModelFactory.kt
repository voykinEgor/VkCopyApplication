package com.example.vknews.presentation.commentsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vknews.domain.entities.DataPostCard

class CommentsViewModelFactory (
    val post: DataPostCard
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(post) as T
    }
}