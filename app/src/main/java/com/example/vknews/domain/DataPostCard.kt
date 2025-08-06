package com.example.vknews.domain

import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.example.vknews.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPostCard (
    val id: Int,
    val communityName: String = "/dev/null",
    val publishDate: String = "14:00",
    val avatarRes: Int = R.drawable.post_comunity_thumbnail,
    val postText: String = "влыдфаожфвыдолаждфвылоадфыовадлжыфвоафждлываы",
    val postImageRes: Int = R.drawable.post_content_image,
    val statistics: List<StatisticsItem> = listOf(
        StatisticsItem(TypeStatistics.VIEWS, 916),
        StatisticsItem(TypeStatistics.REPOSTS, 5),
        StatisticsItem(TypeStatistics.COMMENTS, 6),
        StatisticsItem(TypeStatistics.LIKES, 23)
    )
): Parcelable{
    companion object{
        val PostNavigationType = object : NavType<DataPostCard>(false){
            override fun put(
                bundle: SavedState,
                key: String,
                value: DataPostCard
            ) {
                bundle.putParcelable(key, value)
            }

            override fun get(
                bundle: SavedState,
                key: String
            ): DataPostCard? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, DataPostCard::class.java)
                } else {
                    bundle.getParcelable<DataPostCard>(key)
                }
            }

            override fun parseValue(value: String): DataPostCard {
                return Gson().fromJson(value, DataPostCard::class.java)
            }

        }
    }
}