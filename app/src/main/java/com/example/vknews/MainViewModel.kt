package com.example.vknews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.FeedPostRepositoryImpl
import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.domain.useCases.ChangeLikeStatusUseCase
import com.example.vknews.domain.useCases.GetPostsUseCase
import com.example.vknews.domain.useCases.IgnorePostUseCase
import com.example.vknews.domain.useCases.UpdatePostsUseCase
import com.example.vknews.extensions.mergeWith
import com.example.vknews.presentation.postScreen.PostsState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = FeedPostRepositoryImpl()

    private val getPostsUseCase = GetPostsUseCase(repository)
    private val updatePostsUseCase = UpdatePostsUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = IgnorePostUseCase(repository)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("LOG_TAG1", "exceptionHandler caught exception")
    }

    val loadingState = MutableSharedFlow<PostsState>()
    val screenState = getPostsUseCase()
        .filter { it.isNotEmpty() }
        .map { PostsState.Posts(it) }
        .onStart { PostsState.Loading }
        .mergeWith(loadingState)



    fun loadNextPosts() {
        viewModelScope.launch(exceptionHandler) {
            loadingState.emit(PostsState.Posts(repository.postsList, true))
            updatePostsUseCase()
        }
    }

    fun changeLikeStatus(feedPost: DataPostCard) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun deletePost(postCard: DataPostCard) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(postCard)
        }
    }
}