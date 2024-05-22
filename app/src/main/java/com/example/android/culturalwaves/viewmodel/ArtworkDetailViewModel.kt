package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import com.example.android.culturalwaves.data.repositories.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.android.culturalwaves.utils.Result


//class ArtworkDetailViewModel(private val artRepository: ArtRepository, private val objectId: Int) : BaseViewModel() {
//    private val _artworkDetails = MutableStateFlow<ArtworkDetailResponse?>(null)
//    val artworkDetails: StateFlow<ArtworkDetailResponse?> get() = _artworkDetails.asStateFlow()
//
//    init {
//        fetchArtworkDetails()
//    }
//
//    private fun fetchArtworkDetails() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            when (val result = artRepository.fetchArtworkDetails(objectId)) {
//                is Result.Success -> {
//                    _artworkDetails.value = result.data
//                    _error.value = null
//                }
//                is Result.Error -> {
//                    _artworkDetails.value = null
//                    _error.value = result.exception.message
//                }
//            }
//            _isLoading.value = false
//        }
//    }
//}


class ArtworkDetailViewModel(private val artRepository: ArtRepository, private val objectId: Int) : BaseViewModel() {
    private val _artworkDetails = MutableStateFlow<ArtworkDetailResponse?>(null)
    val artworkDetails: StateFlow<ArtworkDetailResponse?> get() = _artworkDetails.asStateFlow()

    init {
        fetchArtworkDetails()
    }

    private fun fetchArtworkDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = artRepository.fetchArtworkDetails(objectId)) {
                is Result.Success -> {
                    _artworkDetails.value = result.data
                    _error.value = null
                }
                is Result.Error -> {
                    _artworkDetails.value = null
                    _error.value = result.exception.message
                }
            }
            _isLoading.value = false
        }
    }
}