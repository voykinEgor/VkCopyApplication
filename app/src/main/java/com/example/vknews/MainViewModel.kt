package com.example.vknews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.ApiFactory
import com.example.vknews.data.ApiService
import com.example.vknews.data.FeedPostRepository
import com.example.vknews.data.mapper.FeedPostMapper
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.extensions.mergeWith
import com.example.vknews.presentation.postScreen.PostsState
import com.vk.id.VKID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = FeedPostRepository()
    private val loadingState = repository.loadPosts

    private val nextPostsActionState = MutableSharedFlow<PostsState>()

    val screenState = loadingState
        .filter { it.isNotEmpty() }
        .map{ PostsState.Posts(it) as PostsState}
        .onStart { emit(PostsState.Loading) }
        .mergeWith(nextPostsActionState)

    fun loadNextPosts(){
        viewModelScope.launch {
            nextPostsActionState.emit(PostsState.Posts(loadingState.value, true))
            repository.loadNextData()
        }
    }

    fun changeLikeStatus(feedPost: DataPostCard) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun deletePost(postCard: DataPostCard) {
        viewModelScope.launch {
            repository.ignoreItem(postCard)
        }
    }
}