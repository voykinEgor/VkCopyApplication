package com.example.vknews.data

import com.example.vknews.data.entities.CommentsResponseDto
import com.example.vknews.data.entities.ResponseFeedPost
import com.example.vknews.data.entities.statistics.LikesCountResponseDto
import com.vk.id.AccessToken
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.199")
    suspend fun getFeedPosts(@Query("access_token") token: String): ResponseFeedPost

    @GET("newsfeed.get?v=5.199")
    suspend fun getFeedPosts(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): ResponseFeedPost

    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.199&type=wall")
    suspend fun ignoreItem(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    )

    @GET("wall.getComments?v=5.199&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") itemId: Long
    ): CommentsResponseDto
}