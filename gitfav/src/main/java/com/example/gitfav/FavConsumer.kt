package com.example.gitfav

import android.net.Uri

data class FavConsumer(var username: String, var avatar_url: String, var name: String) {
    companion object {
        const val ID = "uid"
        const val USERNAME = "username"
        const val AVATAR_URL = "avatar_url"
        const val NAME = "name"

        const val AUTHORITY = "com.example.githubuserdetailed"
        private const val SCHEME = "content"
        const val TABLE_NAME = "favorites"
        val CONTENT_URI: Uri = Uri.Builder().apply {
            scheme(SCHEME)
            authority(AUTHORITY)
            appendPath(TABLE_NAME)
        }.build()
    }
}
