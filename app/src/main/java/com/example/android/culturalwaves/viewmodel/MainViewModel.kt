package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.domain.repository.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


//class ArtViewModel(private val artRepository: ArtRepository) : ViewModel() {
//    val artworks: StateFlow<PagingData<Artwork>> = artRepository.getArtworksStream()
//        .cachedIn(viewModelScope)
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
//}
//
//


class MainViewModel(private val artRepository: ArtRepository) : ViewModel() {
    val artworks: StateFlow<PagingData<Artwork>> = artRepository.getArtworksStream()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private val _artworkLoadStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> = _artworkLoadStates

    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }


}

