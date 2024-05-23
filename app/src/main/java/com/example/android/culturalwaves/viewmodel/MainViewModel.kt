package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.repositories.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val artRepository: ArtRepository
) : BaseViewModel() {
    private val _currentClassification
    = MutableStateFlow<String?>(null)
    val currentClassification: StateFlow<String?> get() = _currentClassification.asStateFlow()

    private val _artworks
    = MutableStateFlow<PagingData<Artwork>>(PagingData.empty())
    val artworks: StateFlow<PagingData<Artwork>> get() = _artworks.asStateFlow()

    private val _artworkLoadStates
    = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> get() = _artworkLoadStates.asStateFlow()

    init {
        viewModelScope.launch {
            currentClassification.collectLatest { classification ->
                refreshArtworks(classification)
            }
        }
    }

    fun refreshArtworks(classification: String? = _currentClassification.value) {
        viewModelScope.launch {
            _isLoading.value = true
            val queryParams = classification?.let { mapOf("classification" to it) } ?: emptyMap()
            artRepository.getArtworksStream(queryParams)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _artworks.value = pagingData
                    _isLoading.value = false
                }
        }
    }

    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }

    fun toggleClassification(classification: String) {
        if (_currentClassification.value == classification) {
            _currentClassification.value = null
        } else {
            _currentClassification.value = classification
        }
    }
}