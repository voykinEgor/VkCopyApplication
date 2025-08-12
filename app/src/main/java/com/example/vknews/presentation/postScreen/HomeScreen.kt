package com.example.vknews.presentation.postScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.vknews.MainViewModel
import com.example.vknews.domain.entities.DataPostCard
import com.example.vknews.domain.entities.StatisticsItem
import com.example.vknews.domain.entities.TypeStatistics
import com.example.vknews.ui.theme.DarkBlue

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    listPosts: List<DataPostCard>,
    viewModel: MainViewModel,
    nextDataIsLoading: Boolean,
    goToCommentScreen: (DataPostCard) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(listPosts, key = { it.id }) { postCard ->
            val dismissThresholds = with(LocalDensity.current) {
                LocalConfiguration.current.screenWidthDp.dp.toPx() * 0.7f
            }
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    val isDismissed = value in setOf(
                        SwipeToDismissBoxValue.EndToStart,
                        SwipeToDismissBoxValue.StartToEnd
                    )
                    if (isDismissed) viewModel.deletePost(postCard)
                    return@rememberSwipeToDismissBoxState isDismissed
                },
                positionalThreshold = { dismissThresholds }
            )
            SwipeToDismissBox(
                modifier = Modifier.animateItem(),
                state = dismissState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(Color.Transparent)
                    )
                }
            ) {
                PostCard(
                    modifier = Modifier.padding(8.dp),
                    postCardInfo = postCard,
                    onStatisticItemClick = { clickableItem ->
                        determineTypeClickListeners(
                            viewModel,
                            clickableItem,
                            postCard,
                            goToCommentScreen = goToCommentScreen
                        )
                    }
                )
            }

        }
        item {
            if (nextDataIsLoading){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            }else{
                SideEffect {
                    viewModel.loadNextPosts()
                }
            }
        }
    }
}

private fun determineTypeClickListeners(
    viewModel: MainViewModel,
    item: StatisticsItem,
    postCard: DataPostCard,
    goToCommentScreen: (DataPostCard) -> Unit
) {
    when (item.type) {
        TypeStatistics.VIEWS -> {}
        TypeStatistics.LIKES -> viewModel.changeLikeStatus(postCard)
        TypeStatistics.COMMENTS -> goToCommentScreen(postCard)
        TypeStatistics.REPOSTS -> {}
    }
}