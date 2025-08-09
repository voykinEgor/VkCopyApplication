package com.example.vknews.data.mapper

import android.util.Log
import com.example.vknews.data.entities.CommentsResponseDto
import com.example.vknews.data.entities.ResponseFeedPost
import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.domain.TypeStatistics
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
                ownerId = post.ownerId,
                communityName = group.name,
                publishDate = getSimpleDate(post.date),
                avatarUrl = group.photo,
                postText = post.text,
                postImageUrl = post.attachments?.firstOrNull()?.photo?.photos?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticsItem(TypeStatistics.COMMENTS, post.comments.count),
                    StatisticsItem(TypeStatistics.VIEWS, post.views.count),
                    StatisticsItem(TypeStatistics.LIKES, post.likes.count),
                    StatisticsItem(TypeStatistics.REPOSTS, post.reposts.count),
                    ),
                isFavorite = post.likes.isUserLikes > 0
            )
            listEntities.add(postEntity)
        }

        return listEntities
    }

    fun mapResponseToComments(response: CommentsResponseDto): List<CommentItem>{
        val listComments = mutableListOf<CommentItem>()

        val comments = response.response.commentsList
        val profiles = response.response.listProfiles

        for (comment in comments){
            val profile = profiles.firstOrNull{it.id == comment.authorId} ?: continue
            val commentEntity = CommentItem(
                id = comment.id,
                authorName = "${profile.firstName} ${profile.lastName}",
                authorImageUrl = profile.usersPhotoUrl,
                commentText = comment.text,
                publicationTime = getSimpleDate(comment.date)
            )
            listComments.add(commentEntity)
        }

        return listComments
    }
    private fun getSimpleDate(timeMillis: Long): String{
        val date = Date(timeMillis * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}
