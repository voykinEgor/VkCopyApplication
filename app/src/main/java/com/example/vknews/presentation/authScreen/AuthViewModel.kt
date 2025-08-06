package com.example.vknews.presentation.authScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vk.id.AccessToken
import com.vk.id.VKID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel: ViewModel() {

    private val _auth = MutableStateFlow<AuthState>(AuthState.Initial)
    val auth = _auth.asStateFlow()

    init {
        val token = VKID.instance.accessToken
        Log.d("LOG_TAG1", "Token: ${token?.token}")
        _auth.value = if (token != null) AuthState.Authorized(token) else AuthState.NotAuthorized
    }

    fun performedAuthorized(token: AccessToken){
        _auth.value = if (VKID.instance.accessToken != null) AuthState.Authorized(VKID.instance.accessToken!!) else AuthState.NotAuthorized
    }


}