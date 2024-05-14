package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.culturalwaves.domain.repository.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CategoryViewModel(private val artRepository: ArtRepository) : ViewModel() {
    private val _selectedCategories = MutableStateFlow<Set<String>>(emptySet())
    val selectedCategories: StateFlow<Set<String>> = _selectedCategories.asStateFlow()
}