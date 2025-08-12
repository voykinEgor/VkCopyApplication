package com.example.vknews.domain.useCases

import com.example.vknews.domain.repository.FeedPostRepository

class UpdateAuthStateUseCase(
    private val repository: FeedPostRepository
) {
    suspend operator fun invoke() = repository.updateAuthState()
}