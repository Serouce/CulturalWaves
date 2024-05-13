package com.example.android.culturalwaves.data.dao

import androidx.room.*
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteArtworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteArtwork: FavoriteArtwork)

    @Delete
    suspend fun delete(favoriteArtwork: FavoriteArtwork)

    @Query("SELECT * FROM favorite_artworks")
    fun getAllFavorites(): Flow<List<FavoriteArtwork>>

    @Query("SELECT * FROM favorite_artworks WHERE objectId = :objectId LIMIT 1")
    suspend fun getFavoriteById(objectId: Int): FavoriteArtwork?
}