package com.example.vknews.data

import com.example.vknews.data.entities.ResponseFeedPost
import com.example.vknews.data.entities.statistics.LikesCountResponseDto
import com.vk.id.AccessToken
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.199")
    suspend fun getFeedPosts(@Query("access_token") token: String): ResponseFeedPost

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
}