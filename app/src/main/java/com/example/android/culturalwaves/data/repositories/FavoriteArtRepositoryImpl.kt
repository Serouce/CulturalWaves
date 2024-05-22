package com.example.android.culturalwaves.data.repositories

import com.example.android.culturalwaves.data.dao.FavoriteArtworkDao
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import kotlinx.coroutines.flow.Flow

class FavoriteArtRepositoryImpl(private val favoriteArtworkDao: FavoriteArtworkDao) : FavoriteArtRepository {
    override suspend fun addFavorite(artwork: FavoriteArtwork) {
        favoriteArtworkDao.insert(artwork)
    }

    override suspend fun removeFavorite(artwork: FavoriteArtwork) {
        favoriteArtworkDao.delete(artwork)
    }

    override fun getAllFavoritesNewestFirst(): Flow<List<FavoriteArtwork>> {
        return favoriteArtworkDao.getAllFavoritesNewestFirst()
    }

    override fun getAllFavoritesOldestFirst(): Flow<List<FavoriteArtwork>> {
        return favoriteArtworkDao.getAllFavoritesOldestFirst()
    }

    override suspend fun getFavoriteById(objectId: Int): FavoriteArtwork? {
        return favoriteArtworkDao.getFavoriteById(objectId)
    }
}