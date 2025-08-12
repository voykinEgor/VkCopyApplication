package com.example.vknews.domain.useCases

import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.domain.repository.FeedPostRepository

class ChangeLikeStatusUseCase(
    private val repository: FeedPostRepository
) {
    suspend operator fun invoke(feedPost: DataPostCard) = repository.changeLikeStatus(feedPost)
}