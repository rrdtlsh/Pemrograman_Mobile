package com.example.mypokedexapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedexapp.data.local.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val isDarkTheme = settingsDataStore.isDarkTheme

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setDarkTheme(isDark)
        }
    }
}