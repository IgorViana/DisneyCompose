package com.vianabrothers.android.myapplication.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vianabrothers.android.myapplication.model.DisneyCharactersResponse
import com.vianabrothers.android.myapplication.repository.DisneyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val disneyRepository: DisneyRepository
) : ViewModel() {

    init {
        loadCharactersList()
    }

    private val _charactersList: MutableState<List<DisneyCharactersResponse>> = mutableStateOf(
        emptyList()
    )
    val charactersList = _charactersList

    fun loadCharactersList() {
        viewModelScope.launch {
            val response = disneyRepository.getDisneyCharacters()

            _charactersList.value = response.data
        }
    }


}