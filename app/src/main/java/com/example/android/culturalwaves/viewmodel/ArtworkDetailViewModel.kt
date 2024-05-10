package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.culturalwaves.model.ArtworkDetailResponse
import com.example.android.culturalwaves.repository.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtworkDetailViewModel(
    private val artRepository: ArtRepository,
    private val objectId: Int
) : ViewModel() {

    private val _artworkDetails = MutableStateFlow<ArtworkDetailResponse?>(null)
    val artworkDetails: StateFlow<ArtworkDetailResponse?> = _artworkDetails.asStateFlow()

    init {
        fetchArtworkDetails()
    }

    private fun fetchArtworkDetails() {
        viewModelScope.launch {
            _artworkDetails.value = artRepository.fetchArtworkDetails(objectId)
        }
    }
}
