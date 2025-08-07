package com.example.vknews.presentation.postScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vknews.R
import com.example.vknews.domain.DataPostCard
import com.example.vknews.domain.StatisticsItem
import com.example.vknews.domain.TypeStatistics


@Composable
fun PostCard(
    modifier: Modifier,
    postCardInfo: DataPostCard,
    onStatisticItemClick: (StatisticsItem) -> Unit
) {
    Card(modifier = modifier) {
        ProfileView(postCardInfo)

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = postCardInfo.postText
            )
            Spacer(modifier = Modifier.height(8.dp))
            Log.d("LOG_TAG1", "AvatarImage: ${postCardInfo.postImageUrl}")
            AsyncImage(
                model = postCardInfo.postImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth

            )

            Spacer(modifier = Modifier.height(8.dp))

            Statistics(postCardInfo.statistics, onStatisticItemClick)
        }
    }
}

@Composable
fun Statistics(
    statistics: List<StatisticsItem>,
    onItemClick: (StatisticsItem) -> Unit
) {
    Row {
        Row(modifier = Modifier.weight(1f)) {
            val viewsItem = statistics.findByType(TypeStatistics.VIEWS)
            IconWithText(
                R.drawable.eye,
                viewsItem.count.toString(),
                {onItemClick(viewsItem)}
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val repostsItem = statistics.findByType(TypeStatistics.REPOSTS)
            val commentItem = statistics.findByType(TypeStatistics.COMMENTS)
            val likesItem = statistics.findByType(TypeStatistics.LIKES)
            IconWithText(
                R.drawable.ic_share,
                repostsItem.count.toString(),
                { onItemClick(repostsItem) }
            )
            IconWithText(
                R.drawable.comment,
                commentItem.count.toString(),
                { onItemClick(commentItem) }
            )
            IconWithText(
                R.drawable.like_empty,
                likesItem.count.toString(),
                { onItemClick(likesItem) }
            )
        }
    }


}

private fun List<StatisticsItem>.findByType(type: TypeStatistics): StatisticsItem {
    return this.find { it.type == type }
        ?: throw IllegalArgumentException("StatisticsItem with type: $type not found")
}

@Composable
fun IconWithText(
    iconId: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(iconId),
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = text
        )
    }

}

@Composable
fun ProfileView(
    postCardInfo: DataPostCard
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Log.d("LOG_TAG1", "AvatarImage: ${postCardInfo.avatarUrl}")
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            model = postCardInfo.avatarUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = postCardInfo.communityName
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                color = MaterialTheme.colorScheme.onSecondary,
                text = postCardInfo.publishDate
            )
        }

        Icon(
            tint = MaterialTheme.colorScheme.onSecondary,
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null
        )
    }
}