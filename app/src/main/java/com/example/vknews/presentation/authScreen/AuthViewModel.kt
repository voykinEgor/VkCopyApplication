package com.example.vknews.presentation.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.FeedPostRepositoryImpl
import com.example.vknews.domain.useCases.GetAuthStateUseCase
import com.example.vknews.domain.useCases.UpdateAuthStateUseCase
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val repository = FeedPostRepositoryImpl()
    private val authUseCase = GetAuthStateUseCase(repository)
    private val updateAuthStateUseCase = UpdateAuthStateUseCase(repository)
    val auth = authUseCase.invoke()

    fun performedAuthorized(){
        viewModelScope.launch {
            updateAuthStateUseCase.invoke()
        }
    }
}