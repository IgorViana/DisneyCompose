package com.vianabrothers.android.myapplication.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vianabrothers.android.myapplication.model.DetailCharacterResponse
import com.vianabrothers.android.myapplication.repository.DisneyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DisneyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = savedStateHandle.get<String>("id").orEmpty()

    private val _detail: MutableState<DetailCharacterResponse?> = mutableStateOf(null)
    val characterDetail = _detail

    init {
        viewModelScope.launch {
            val response = repository.getDisneyCharactersDetail(id)
            _detail.value = response
        }
    }

}