package com.example.githubuserdetailed.dao

import android.database.Cursor

class DBHelperImpl(private val appDatabase: AppDatabase) : DBHelper {
    override suspend fun getFavorites(): List<Favorites> = appDatabase.favDao().getAll()

    override suspend fun insertAll(favorites: List<Favorites>) = appDatabase.favDao().insertAll(favorites)
    override suspend fun addFav(favorites: Favorites) = appDatabase.favDao().addFav(favorites)
    override suspend fun deleteFav(username: String) = appDatabase.favDao().deleteFav(username)
    override suspend fun getFavbyUsername(username: String?) = appDatabase.favDao().getFavbyUsername(username)
    override fun getAllCursor(): Cursor = appDatabase.favDao().getAllCursor()
}