package com.example.vknews

import androidx.lifecycle.ViewModel
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.presentation.postScreen.PostsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val initialList = mutableListOf<DataPostCard>().apply {
        repeat(10) {
            add(DataPostCard(id = it))
        }
    }
    private val initialState = PostsState.Posts(initialList)
    private val _postsState = MutableStateFlow<PostsState>(initialState)
    val screenState = _postsState.asStateFlow()

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