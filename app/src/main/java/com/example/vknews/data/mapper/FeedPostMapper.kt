package com.example.vknews.data.mapper

import android.util.Log
import com.example.vknews.data.entities.ResponseFeedPost
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.domain.TypeStatistics
import kotlin.math.absoluteValue

class FeedPostMapper{

    fun mapFeedPostsDtoToEntities(response: ResponseFeedPost): List<DataPostCard>{
        val listEntities = mutableListOf<DataPostCard>()

        val listPosts = response.response.posts.filter {
            it.views != null && it.reposts != null && it.comments != null && it.likes != null
        }
        val listGroups = response.response.groups

        for (post in listPosts){
            val group = listGroups.find { it.id == post.ownerId.absoluteValue } ?: continue
            val postEntity = DataPostCard(
                id = post.id,
                communityName = group.name,
                publishDate = post.date.toString(),
                avatarUrl = group.photo,
                postText = post.text,
                postImageUrl = post.attachments?.firstOrNull()?.photo?.photos?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticsItem(TypeStatistics.COMMENTS, post.comments.count),
                    StatisticsItem(TypeStatistics.VIEWS, post.views.count),
                    StatisticsItem(TypeStatistics.LIKES, post.likes.count),
                    StatisticsItem(TypeStatistics.REPOSTS, post.reposts.count),
                    )
            )
            listEntities.add(postEntity)
        }

        return listEntities
    }
}
