package com.example.vknews.domain.entities

import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPostCard (
    val id: Long,
    val communityName: String,
    val ownerId: Long,
    val publishDate: String,
    val avatarUrl: String,
    val postText: String,
    val postImageUrl: String?,
    val statistics: List<StatisticsItem>,
    val isFavorite: Boolean
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