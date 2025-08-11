package com.example.vknews.data

import com.example.vknews.data.ApiFactory.apiService
import com.example.vknews.data.mapper.FeedPostMapper
import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.domain.TypeStatistics
import com.example.vknews.extensions.mergeWith
import com.vk.id.VKID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class FeedPostRepository {
    private val mapper = FeedPostMapper()

    private val coroutineContext = CoroutineScope(Dispatchers.IO)
    private val actionState = MutableSharedFlow<Unit>(replay = 1)

    val refreshedListState = MutableSharedFlow<List<DataPostCard>>()

    private val _postsList = mutableListOf<DataPostCard>()
    private val postsList get() = _postsList.toList()
    val token =
        VKID.instance.accessToken?.token ?: throw IllegalArgumentException("Отсутствует токен")

    private var nextFrom: String? = null

    val loadPosts: StateFlow<List<DataPostCard>> = flow{
        actionState.emit(Unit)
        actionState.collect{
            val startFrom = nextFrom
            if (startFrom == null && postsList.isNotEmpty()) emit(postsList)
            val response =
                if (startFrom == null) apiService.getFeedPosts(token) else apiService.getFeedPosts(
                    token,
                    startFrom
                )
            nextFrom = response.response.nextFrom
            val listDataPostCard = mapper.mapFeedPostsDtoToEntities(response)
            _postsList.addAll(listDataPostCard)
            emit(postsList)
        }
    }.mergeWith(refreshedListState)
        .stateIn(
        scope = coroutineContext,
        started = SharingStarted.Lazily,
        initialValue = postsList
    )

    suspend fun loadNextData(){
        actionState.emit(Unit)
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
        val postIndex =
            _postsList.indexOfFirst { it.id == feedPost.id && it.ownerId == feedPost.ownerId }
        _postsList[postIndex] = newPost
        refreshedListState.emit(postsList)
    }

    suspend fun ignoreItem(feedPost: DataPostCard){
        apiService.ignoreItem(token, feedPost.ownerId, feedPost.id)
        _postsList.remove(feedPost)
        refreshedListState.emit(postsList)
    }

    suspend fun getComments(feedPost: DataPostCard): List<CommentItem>{
        val response = apiService.getComments(token, feedPost.ownerId, feedPost.id)
        val listComments = mapper.mapResponseToComments(response).filter { it.commentText.isNotBlank() }
        return listComments
    }
}