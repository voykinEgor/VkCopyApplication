package com.example.vknews.presentation.postScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.MainViewModel
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.domain.TypeStatistics

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    listPosts: List<DataPostCard>,
    goToCommentScreen: (DataPostCard) -> Unit
){
    Log.d("LOG_TAG1", "Recomposition HomeScreen")
    val viewModel: MainViewModel = viewModel()
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
    }
}

private fun determineTypeClickListeners(
    viewModel: MainViewModel,
    item: StatisticsItem,
    postCard: DataPostCard,
    goToCommentScreen: (DataPostCard) -> Unit
) {
    when (item.type) {
        TypeStatistics.VIEWS -> viewModel.updateCount(postCard, item)
        TypeStatistics.LIKES -> viewModel.changeLikeStatus(postCard)
        TypeStatistics.COMMENTS -> goToCommentScreen(postCard)
        TypeStatistics.REPOSTS -> viewModel.updateCount(postCard, item)
    }
}