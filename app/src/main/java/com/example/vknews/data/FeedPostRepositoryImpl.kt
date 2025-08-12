package com.example.vknews.data

import com.example.vknews.data.ApiFactory.apiService
import com.example.vknews.data.mapper.FeedPostMapper
import com.example.vknews.domain.entities.CommentItem
import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.domain.entities.StatisticsItem
import com.example.vknews.domain.entities.TypeStatistics
import com.example.vknews.domain.repository.FeedPostRepository
import com.example.vknews.extensions.mergeWith
import com.example.vknews.presentation.authScreen.AuthState
import com.vk.id.VKID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class FeedPostRepositoryImpl: FeedPostRepository {
    private val mapper = FeedPostMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _postsList = mutableListOf<DataPostCard>()
    val postsList get() = _postsList.toList()

    val token
      get() = VKID.instance.accessToken?.token ?: throw IllegalArgumentException("Отсутствует токен")

    private val flowLoadingPosts = flow {
        updatePostsState.emit(Unit)
        updatePostsState.collect {
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
    }.retry {
        delay(3000)
        true
    }



    private var nextFrom: String? = null

    private val refreshedState = MutableSharedFlow<List<DataPostCard>>()

    private val updatePostsState = MutableSharedFlow<Unit>(replay = 1)
    private val updateAuthState = MutableSharedFlow<Unit>(replay = 1)

    override fun getAuthState(): StateFlow<AuthState> = flow {
        updateAuthState.emit(Unit)
        updateAuthState.collect {
            val tokenFlow = VKID.instance.accessToken?.token
            emit(if (tokenFlow != null) AuthState.Authorized(VKID.instance.accessToken!!) else AuthState.NotAuthorized)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getPosts(): StateFlow<List<DataPostCard>> = flowLoadingPosts
        .mergeWith(refreshedState)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = postsList
        )

    override suspend fun updatePosts() {
        updatePostsState.emit(Unit)
    }

    override suspend fun updateAuthState() {
        updateAuthState.emit(Unit)
    }

    override suspend fun changeLikeStatus(feedPost: DataPostCard) {
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
        refreshedState.emit(postsList)
    }

    override suspend fun ignoreItem(feedPost: DataPostCard) {
        apiService.ignoreItem(token, feedPost.ownerId, feedPost.id)
        _postsList.remove(feedPost)
        refreshedState.emit(postsList)
    }

    override fun getComments(feedPost: DataPostCard): StateFlow<List<CommentItem>> = flow {
        val response = apiService.getComments(token, feedPost.ownerId, feedPost.id)
        val listComments =
            mapper.mapResponseToComments(response).filter { it.commentText.isNotBlank() }
        emit(listComments)
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        emptyList()
    )
}