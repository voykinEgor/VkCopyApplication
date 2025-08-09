package com.example.vknews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.ApiFactory
import com.example.vknews.data.ApiService
import com.example.vknews.data.FeedPostRepository
import com.example.vknews.data.mapper.FeedPostMapper
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.presentation.postScreen.PostsState
import com.vk.id.VKID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val initialState = PostsState.Initial
    private val _postsState = MutableStateFlow<PostsState>(initialState)
    val screenState = _postsState.asStateFlow()

    val repository = FeedPostRepository()

    init {
        _postsState.value = PostsState.Loading
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _postsState.value = PostsState.Posts(repository.loadPosts())
        }
    }

    fun loadNextPosts() {
        _postsState.value = PostsState.Posts(repository.postsList, true)
        loadPosts()
    }

    fun changeLikeStatus(feedPost: DataPostCard) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _postsState.value = PostsState.Posts(repository.postsList)
        }
    }

    fun deletePost(postCard: DataPostCard) {
        viewModelScope.launch {
            repository.ignoreItem(postCard)
            _postsState.value = PostsState.Posts(repository.postsList)
        }
    }
}