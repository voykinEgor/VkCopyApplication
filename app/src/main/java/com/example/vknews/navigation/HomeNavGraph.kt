package com.example.vknews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknews.domain.entities.DataPostCard

fun NavGraphBuilder.homeNavGraph(
    commentsScreen: @Composable (DataPostCard) -> Unit,
    postsScreen: @Composable () -> Unit
) {
    navigation(startDestination = Screen.Posts.route, route = Screen.Home.route) {
        composable(Screen.Posts.route) {
            postsScreen()
        }

        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_POST){
                    type = DataPostCard.PostNavigationType
                }
            )
        ) {
            val post = it.arguments?.getParcelable<DataPostCard>(Screen.KEY_POST) ?: throw RuntimeException("Args in Comments is null")

            commentsScreen(post)
        }
    }
}