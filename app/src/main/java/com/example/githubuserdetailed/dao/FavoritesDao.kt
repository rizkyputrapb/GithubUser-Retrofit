package com.example.githubuserdetailed.dao

import android.database.Cursor
import androidx.room.*


@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<Favorites>

    @Insert
    suspend fun insertAll(favorites: List<Favorites>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(favorites: Favorites)

    @Query("DELETE FROM favorites WHERE username = :username")
    suspend fun deleteFav(username: String)

    @Query("SELECT * FROM favorites WHERE username = :username")
    suspend fun getFavbyUsername(username: String?): Favorites

    @Query("SELECT * FROM favorites WHERE uid = :uid")
    suspend fun getFavbyUid(uid: Int?): Favorites

    @Query("SELECT * FROM favorites")
    fun getAllCursor(): Cursor

    @Delete
    suspend fun delete(favorites: Favorites)
}