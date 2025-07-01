package com.example.mypokedexapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedexapp.domain.use_case.GetFavoritePokemonsUseCase
import com.example.mypokedexapp.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritePokemonsUseCase: GetFavoritePokemonsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val favoritesState = getFavoritePokemonsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun toggleFavorite(pokemonName: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(pokemonName, isFavorite)
        }
    }
}