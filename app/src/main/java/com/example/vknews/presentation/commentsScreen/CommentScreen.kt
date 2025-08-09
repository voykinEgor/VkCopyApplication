package com.example.vknews.presentation.commentsScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.vknews.R
import com.example.vknews.domain.CommentItem
import com.example.vknews.domain.DataPostCard
import com.example.vknews.ui.theme.VkNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    onBackPressed: () -> Unit,
    postCard: DataPostCard
) {
    val viewModel: CommentsViewModel = viewModel(factory = CommentsViewModelFactory(postCard))
    val screenState = viewModel.commentsState.collectAsState()
    val currentState = screenState.value
    if (currentState is CommentsState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Комментарии") },
                    navigationIcon = {
                        IconButton(
                            onClick = { onBackPressed() }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(
                    items = currentState.comments,
                    key = { it.id }
                ) { comment ->
                    CommentItemView(comment)

                    BackHandler {
                        onBackPressed()
                    }
                }
            }
        }
    }

}

@Composable
private fun CommentItemView(
    commentItem: CommentItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = commentItem.authorImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = commentItem.authorName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = commentItem.commentText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = commentItem.publicationTime,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}