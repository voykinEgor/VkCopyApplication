package com.example.vknews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.presentation.authScreen.AuthScreen
import com.example.vknews.presentation.authScreen.AuthState
import com.example.vknews.presentation.authScreen.AuthViewModel
import com.example.vknews.presentation.postScreen.MainScreen
import com.example.vknews.ui.theme.VkNewsTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkNewsTheme {
                val viewModel: AuthViewModel = viewModel()
                val authState = viewModel.auth.collectAsState()
                when(authState.value){
                    is AuthState.Authorized -> {
                        MainScreen()
                    }
                    AuthState.NotAuthorized -> {
                        AuthScreen(viewModel)
                    }
                    else -> {}
                }
            }
        }
    }
}