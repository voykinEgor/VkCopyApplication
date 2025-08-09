package com.example.vknews.presentation.postScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vknews.MainViewModel
import com.example.vknews.domain.DataPostCard
import com.example.vknews.navigation.NavGraph
import com.example.vknews.navigation.rememberNavigationState
import com.example.vknews.presentation.commentsScreen.CommentScreen
import com.example.vknews.ui.theme.DarkBlue

@Composable
private fun MainScreen(paddingValues: PaddingValues, onCommentClickListener: (DataPostCard) -> Unit) {
    val viewModel: MainViewModel = viewModel()
    val screenState = viewModel.screenState.collectAsState()
    when (val currentState = screenState.value) {
        PostsState.Initial -> {}
        is PostsState.Posts -> HomeScreen(
            paddingValues = paddingValues,
            listPosts = currentState.posts,
            viewModel = viewModel,
            nextDataIsLoading = currentState.isLoading,
            goToCommentScreen = { postCard ->
                onCommentClickListener(postCard)
            }
        )

        PostsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}

@Composable
fun PostsScreen() {
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val items = listOf(BottomItem.Home, BottomItem.Favorite, BottomItem.Profile)
                val getCurrentState by navigationState.navController.currentBackStackEntryAsState()
                items.forEach { item ->
                    val selected = getCurrentState?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = { Text(stringResource(item.textRes)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { paddingValues ->

        NavGraph(
            navHostController = navigationState.navController,
            postsScreen = {
                MainScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = { dataPostCard ->
                        navigationState.navigateComments(dataPostCard)
                    },
                )
            },
            commentsScreen = {postCard ->
                CommentScreen(
                    onBackPressed = {
                        navigationState.navController.popBackStack()
                    },
                    postCard = postCard
                )
            },
            favoriteScreen = { Text(text = "Favorite Content", color = Color.White) },
            profileScreen = { Text(text = "Profile Content", color = Color.White) }
        )

    }
}

