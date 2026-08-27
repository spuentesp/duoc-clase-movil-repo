package com.example.baseproject.ui.screens.exercises.e06_repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryViewModel(
    private val repository: CounterRepository = InMemoryCounterRepository(),
) : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    init {
        viewModelScope.launch { _count.value = repository.getValue() }
    }

    fun increment() {
        viewModelScope.launch {
            val next = _count.value + 1
            repository.saveValue(next)
            _count.value = next
        }
    }
}
