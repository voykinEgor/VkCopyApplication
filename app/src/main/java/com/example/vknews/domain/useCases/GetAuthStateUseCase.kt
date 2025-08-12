package com.example.vknews.domain.useCases

import com.example.vknews.domain.repository.FeedPostRepository

class GetAuthStateUseCase(
    private val repository: FeedPostRepository
) {
    operator fun invoke() = repository.getAuthState()
}