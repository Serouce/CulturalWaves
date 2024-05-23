package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.data.repositories.FavoriteArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class SortOrder {
    NEWEST_FIRST,
    OLDEST_FIRST
}

class FavoriteViewModel(
    private val favoriteArtRepository: FavoriteArtRepository
) : ViewModel() {

    private val _favoriteArtworks
    = MutableStateFlow<List<FavoriteArtwork>>(emptyList())
    val favoriteArtworks: StateFlow<List<FavoriteArtwork>> get() = _favoriteArtworks

    private val _sortOrder = MutableStateFlow(SortOrder.NEWEST_FIRST)
    val sortOrder: StateFlow<SortOrder> get() = _sortOrder

    init {
        viewModelScope.launch {
            _sortOrder.collectLatest { order ->
                when (order) {
                    SortOrder.NEWEST_FIRST -> {
                        favoriteArtRepository.getAllFavoritesNewestFirst().collect {
                            _favoriteArtworks.value = it
                        }
                    }
                    SortOrder.OLDEST_FIRST -> {
                        favoriteArtRepository.getAllFavoritesOldestFirst().collect {
                            _favoriteArtworks.value = it
                        }
                    }
                }
            }
        }
    }

    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    fun addFavorite(artwork: FavoriteArtwork) {
        viewModelScope.launch {
            favoriteArtRepository.addFavorite(artwork)
        }
    }

    fun removeFavorite(artwork: FavoriteArtwork) {
        viewModelScope.launch {
            favoriteArtRepository.removeFavorite(artwork)
        }
    }

    suspend fun isFavorite(objectId: Int): Boolean {
        return favoriteArtRepository.getFavoriteById(objectId) != null
    }
}