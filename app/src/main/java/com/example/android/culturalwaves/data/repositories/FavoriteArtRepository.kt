package com.example.android.culturalwaves.data.repositories

import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import kotlinx.coroutines.flow.Flow

interface FavoriteArtRepository {
    suspend fun addFavorite(artwork: FavoriteArtwork)
    suspend fun removeFavorite(artwork: FavoriteArtwork)
    fun getAllFavoritesNewestFirst(): Flow<List<FavoriteArtwork>>
    fun getAllFavoritesOldestFirst(): Flow<List<FavoriteArtwork>>
    suspend fun getFavoriteById(objectId: Int): FavoriteArtwork?
}