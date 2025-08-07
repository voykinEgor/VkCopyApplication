package com.example.vknews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.ApiFactory
import com.example.vknews.data.ApiService
import com.example.vknews.data.mapper.FeedPostMapper
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.presentation.postScreen.PostsState
import com.vk.id.VKID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val initialState = PostsState.Initial
    private val _postsState = MutableStateFlow<PostsState>(initialState)
    val screenState = _postsState.asStateFlow()

    private val mapper = FeedPostMapper()

    init {
        loadPosts()
    }

    fun loadPosts(){
        val token = VKID.instance.accessToken?.token ?: throw IllegalArgumentException("Отсутствует токен")
        viewModelScope.launch {
            val response = ApiFactory.apiService.getFeedPosts(token)
            val listDataPostCard = mapper.mapFeedPostsDtoToEntities(response)
            _postsState.value = PostsState.Posts(listDataPostCard)
        }

    }

    fun updateCount(postCard: DataPostCard, item: StatisticsItem) {
        val currentState = _postsState.value
        if (currentState !is PostsState.Posts) return
        val oldPosts = currentState.posts
        val newStatistics = postCard.statistics.map { statisticsItem ->
            if (statisticsItem.type == item.type)
                statisticsItem.copy(count = statisticsItem.count + 1)
            else
                statisticsItem
        }
        val newPostCard = postCard.copy(statistics = newStatistics)
        val newPostCardList = oldPosts.map { oldPost ->
            if (oldPost.id == newPostCard.id) {
                newPostCard
            } else {
                oldPost
            }
        }
        _postsState.value = PostsState.Posts(newPostCardList)
    }

    fun deletePost(postCard: DataPostCard) {
        val currentState = _postsState.value
        if (currentState !is PostsState.Posts) return
        val newList = currentState.posts.filterNot { it.id == postCard.id }
        _postsState.value = PostsState.Posts(newList)
    }
}