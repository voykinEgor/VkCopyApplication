package com.example.vknews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.FeedPostRepository
import com.example.vknews.domain.DataPostCard
import com.example.vknews.extensions.mergeWith
import com.example.vknews.navigation.Screen
import com.example.vknews.presentation.postScreen.PostsState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val repository = FeedPostRepository()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("LOG_TAG1", "exceptionHandler caught exception")
    }

    val loadingState = MutableSharedFlow<PostsState>()
    val screenState = repository.postsLoading
        .filter { it.isNotEmpty() }
        .map { PostsState.Posts(it) }
        .onStart { PostsState.Loading }
        .mergeWith(loadingState)



    fun loadNextPosts() {
        viewModelScope.launch(exceptionHandler) {
            loadingState.emit(PostsState.Posts(repository.postsList, true))
            repository.updatePosts()
        }
    }

    fun changeLikeStatus(feedPost: DataPostCard) {
        viewModelScope.launch(exceptionHandler) {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun deletePost(postCard: DataPostCard) {
        viewModelScope.launch(exceptionHandler) {
            repository.ignoreItem(postCard)
        }
    }
}