package com.example.vknews.presentation.authScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vknews.R
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.onetap.common.OneTapStyle
import com.vk.id.onetap.common.button.style.OneTapButtonCornersStyle
import com.vk.id.onetap.common.button.style.OneTapButtonElevationStyle
import com.vk.id.onetap.common.button.style.OneTapButtonSizeStyle
import com.vk.id.onetap.compose.onetap.OneTap

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    viewModel: AuthViewModel
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.vk_logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(100.dp))

            val initializer = VKIDAuthUiParams.Builder().apply {
                scopes = setOf("wall", "friends")
                build()
            }
            OneTap(
                modifier = Modifier.wrapContentHeight().padding(horizontal = 50.dp),
                style = OneTapStyle.Light(
                    cornersStyle = OneTapButtonCornersStyle.Custom(5f),
                    sizeStyle = OneTapButtonSizeStyle.SMALL_38,
                    elevationStyle = OneTapButtonElevationStyle.Custom(4f)
                ),
                onAuth = { _, token ->  viewModel.performedAuthorized()},
                fastAuthEnabled = false,
                authParams = initializer.build()
            )
        }
    }
}