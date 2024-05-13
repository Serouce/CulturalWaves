package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.repository.FavoriteArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteArtRepository: FavoriteArtRepository) : ViewModel() {

    private val _favoriteArtworks = MutableStateFlow<List<FavoriteArtwork>>(emptyList())
    val favoriteArtworks: StateFlow<List<FavoriteArtwork>> = _favoriteArtworks

    init {
        viewModelScope.launch {
            favoriteArtRepository.getAllFavorites().collect { favorites ->
                _favoriteArtworks.value = favorites
            }
        }
    }

    // Добавление в избранное
    fun addFavorite(artwork: FavoriteArtwork) {
        viewModelScope.launch {
            favoriteArtRepository.addFavorite(artwork)
        }
    }

    // Удаление из избранного
    fun removeFavorite(artwork: FavoriteArtwork) {
        viewModelScope.launch {
            favoriteArtRepository.removeFavorite(artwork)
        }
    }

    // Проверка, является ли произведение избранным
    suspend fun isFavorite(objectId: Int): Boolean {
        return favoriteArtRepository.getFavoriteById(objectId) != null
    }
}