package com.example.android.culturalwaves.data.repositories

import com.example.android.culturalwaves.data.dao.FavoriteArtworkDao
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import kotlinx.coroutines.flow.Flow


class FavoriteArtRepository(private val favoriteArtworkDao: FavoriteArtworkDao) {

    // Вставка избранного произведения искусства
    suspend fun addFavorite(artwork: FavoriteArtwork) {
        favoriteArtworkDao.insert(artwork)
    }

    // Удаление избранного произведения искусства
    suspend fun removeFavorite(artwork: FavoriteArtwork) {
        favoriteArtworkDao.delete(artwork)
    }

    // Получение всех избранных произведений искусства
    fun getAllFavorites(): Flow<List<FavoriteArtwork>> {
        return favoriteArtworkDao.getAllFavorites()
    }

    // Получение избранного произведения искусства по его ID
    suspend fun getFavoriteById(objectId: Int): FavoriteArtwork? {
        return favoriteArtworkDao.getFavoriteById(objectId)
    }
}