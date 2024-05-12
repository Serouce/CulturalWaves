package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.repository.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ArtRepository) : ViewModel() {
    private val _searchResults = MutableStateFlow<PagingData<Artwork>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Artwork>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun searchArtworks(query: String, filterOptions: Map<String, String>) {
        _isLoading.value = true
        val queryParams = mutableMapOf<String, String>().apply {
            put("title", query)
            filterOptions.forEach { (key, value) ->
                put(key, value)
            }
        }

        viewModelScope.launch {
            repository.getArtworksStream(queryParams).cachedIn(viewModelScope).collect {
                _searchResults.value = it
                _isLoading.value = false
            }
        }
    }
}
