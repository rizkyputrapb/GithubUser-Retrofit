package com.example.githubuserdetailed.dao

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username"], unique = true)])
data class Favorites(
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "avatar_url") var avatar_url: String?,
    @ColumnInfo(name = "name") var name: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
    companion object{
        const val ID = "uid"
        const val USERNAME = "username"
        const val AVATAR_URL = "avatar_url"
        const val NAME = "name"

        const val AUTHORITY = "com.example.githubuserdetailed"
        private const val SCHEME = "provider"
        const val TABLE_NAME = "favorites"
        val CONTENT_URI: Uri = Uri.Builder().apply {
            scheme(SCHEME)
            authority(AUTHORITY)
            appendPath(TABLE_NAME)
        }.build()
    }
}
