package com.example.vknews.domain.useCases

import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.domain.repository.FeedPostRepository

class GetCommentsUseCase(
    private val repository: FeedPostRepository
) {
    operator fun invoke(feedPost: DataPostCard) = repository.getComments(feedPost)
}