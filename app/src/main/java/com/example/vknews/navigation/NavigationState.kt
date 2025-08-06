package com.example.vknews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vknews.domain.DataPostCard

class NavigationState(
    val navController: NavHostController
) {

    fun navigateTo(route: String){
        navController.navigate(route){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    fun navigateComments(post: DataPostCard){
        navController.navigate(Screen.Comments.getRoute(post))
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState{
    return NavigationState(navHostController)
}