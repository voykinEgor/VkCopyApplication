package com.example.vknews.presentation.postScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknews.R
import com.example.vknews.navigation.Screen

sealed class BottomItem(val screen: Screen, val textRes: Int, val icon: ImageVector) {

    object Home : BottomItem(
        screen = Screen.Home,
        textRes = R.string.bottom_nav_home,
        icon = Icons.Rounded.Home
    )

    object Favorite : BottomItem(
        screen = Screen.Favorite,
        textRes = R.string.bottom_nav_favorite,
        icon = Icons.Rounded.FavoriteBorder
    )

    object Profile : BottomItem(
        screen = Screen.Profile,
        textRes = R.string.bottom_nav_profile,
        icon = Icons.Rounded.Person
    )

}