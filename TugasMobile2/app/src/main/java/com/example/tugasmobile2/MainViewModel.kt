package com.example.tugasmobile2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _data = MutableStateFlow<DataModel?>(null)
    val data: StateFlow<DataModel?> = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.apiService.getData()
                _data.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
