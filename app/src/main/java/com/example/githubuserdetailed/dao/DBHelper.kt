package com.example.githubuserdetailed.dao

import android.database.Cursor

interface DBHelper {
    suspend fun getFavorites(): List<Favorites>
    suspend fun insertAll(favorites: List<Favorites>)
    suspend fun addFav(favorites: Favorites)
    suspend fun deleteFav(username: String)
    suspend fun getFavbyUsername(username: String?): Favorites
    fun getAllCursor(): Cursor
}