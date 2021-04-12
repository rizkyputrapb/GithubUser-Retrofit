package com.example.githubuserdetailed.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao(): FavoritesDao
}