package com.example.vknews.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.vknews.domain.DataPostCard

@Composable
fun NavGraph(
    navHostController: NavHostController,
    favoriteScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    commentsScreen: @Composable (DataPostCard) -> Unit,
    postsScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
        exitTransition = { ExitTransition.None },
        enterTransition = { EnterTransition.None }
    ) {
        homeNavGraph(commentsScreen, postsScreen)

        composable(Screen.Favorite.route) {
            favoriteScreen()
        }

        composable(Screen.Profile.route) {
            profileScreen()
        }
    }
}