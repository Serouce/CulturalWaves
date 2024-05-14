package com.example.android.culturalwaves.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.culturalwaves.data.dao.FavoriteArtworkDao
import com.example.android.culturalwaves.data.entities.FavoriteArtwork

@Database(entities = [FavoriteArtwork::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteArtworkDao(): FavoriteArtworkDao
}