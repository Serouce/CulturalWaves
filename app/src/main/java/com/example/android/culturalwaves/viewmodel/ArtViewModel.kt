package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.repository.ArtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class ArtViewModel(private val artRepository: ArtRepository) : ViewModel() {
    val artworks: StateFlow<PagingData<Artwork>> = artRepository.getArtworksStream()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}

