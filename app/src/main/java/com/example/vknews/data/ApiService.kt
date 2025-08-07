package com.example.vknews.data

import com.example.vknews.data.entities.ResponseFeedPost
import com.vk.id.AccessToken
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.199")
    suspend fun getFeedPosts(@Query("access_token") token: String): ResponseFeedPost

}