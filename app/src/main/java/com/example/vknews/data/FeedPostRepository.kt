package com.example.vknews.data

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.ApiFactory.apiService
import com.example.vknews.data.mapper.FeedPostMapper
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.domain.TypeStatistics
import com.example.vknews.presentation.postScreen.PostsState
import com.example.vknews.presentation.postScreen.Statistics
import com.vk.id.VKID
import kotlinx.coroutines.launch

class FeedPostRepository {
    private val mapper = FeedPostMapper()

    private val _postsList = mutableListOf<DataPostCard>()
    val postsList get() = _postsList.toList()
    val token =
        VKID.instance.accessToken?.token ?: throw IllegalArgumentException("Отсутствует токен")

    suspend fun loadPosts(): List<DataPostCard> {
        val response = ApiFactory.apiService.getFeedPosts(token)
        val listDataPostCard = mapper.mapFeedPostsDtoToEntities(response)
        _postsList.addAll(listDataPostCard)
        return listDataPostCard
    }

    suspend fun changeLikeStatus(feedPost: DataPostCard) {
        val response = if (feedPost.isFavorite) {
            apiService.deleteLike(
                token = token,
                ownerId = feedPost.ownerId,
                itemId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = token,
                ownerId = feedPost.ownerId,
                itemId = feedPost.id
            )
        }
        val newLikesCount = response.likesCount.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == TypeStatistics.LIKES }
            add(StatisticsItem(type = TypeStatistics.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isFavorite = !feedPost.isFavorite)
        val postIndex = _postsList.indexOfFirst { it.id == feedPost.id && it.ownerId == feedPost.ownerId }
        _postsList[postIndex] = newPost
    }
}