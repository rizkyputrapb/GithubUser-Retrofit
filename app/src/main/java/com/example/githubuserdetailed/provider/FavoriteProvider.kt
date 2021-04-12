package com.example.githubuserdetailed.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserdetailed.dao.DBHelperImpl
import com.example.githubuserdetailed.dao.DbBuilder
import com.example.githubuserdetailed.dao.Favorites

class FavoriteProvider : ContentProvider() {

    private lateinit var dbHelper: DBHelperImpl
    companion object {
        private const val CONSUMER = 1
        private val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            URI_MATCHER.addURI(Favorites.AUTHORITY, Favorites.TABLE_NAME, CONSUMER)
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelperImpl(DbBuilder.getInstance(context as Context))
        return true
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (URI_MATCHER.match(uri)) {
            CONSUMER -> dbHelper.getAllCursor()
            else -> null
        }
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}