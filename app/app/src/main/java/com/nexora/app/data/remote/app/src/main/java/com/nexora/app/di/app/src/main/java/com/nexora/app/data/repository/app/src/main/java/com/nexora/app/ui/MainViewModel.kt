package com.nexora.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexora.app.data.local.AnalysisEntity
import com.nexora.app.data.repository.NexoraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NexoraRepository
) : ViewModel() {

    val analyses: StateFlow<List<AnalysisEntity>> = repository.allAnalyses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            repository.refreshAnalyses()
        }
    }

    fun createNewAnalysis(description: String, objective: String) {
        viewModelScope.launch {
            repository.createAnalysis(description, objective)
        }
    }
}
