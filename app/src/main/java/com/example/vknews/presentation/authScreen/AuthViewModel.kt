package com.example.vknews.presentation.authScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.FeedPostRepository
import com.vk.id.AccessToken
import com.vk.id.VKID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    val repository = FeedPostRepository()
    val auth = repository.authFlow

    fun performedAuthorized(){
        viewModelScope.launch {
            repository.updateAuthState()
        }
    }
}