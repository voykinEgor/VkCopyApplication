package com.example.vknews.navigation

import android.net.Uri
import com.example.vknews.domain.entities.DataPostCard
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {

    object Posts: Screen(POSTS)
    object Favorite: Screen(FAVORITE)
    object Profile: Screen(PROFILE)
    object Home: Screen(HOME)
    object Comments: Screen(COMMENTS){
        private const val COMMENTS_FOR_ARGS = "comments_string"

        fun getRoute(postCard: DataPostCard): String{
            val postCardJson = Gson().toJson(postCard)
            return "$COMMENTS_FOR_ARGS/${postCardJson.encode()}"
        }
    }

    companion object{
        const val KEY_POST = "feed_post"
        private const val HOME = "home_string"
        private const val POSTS = "posts_string"
        private const val COMMENTS = "comments_string/{$KEY_POST}"
        private const val FAVORITE = "favorite_string"
        private const val PROFILE = "profile_string"

    }
}

private fun String.encode(): String{
    return Uri.encode(this)
}