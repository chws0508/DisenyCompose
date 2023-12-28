package com.woosuk.disenycompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.disenycompose.domain.model.Poster
import com.woosuk.disenycompose.domain.usecase.GetPostersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostersUseCase: GetPostersUseCase,
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _posters: MutableStateFlow<List<Poster>> = MutableStateFlow(emptyList())
    val posters: StateFlow<List<Poster>> = _posters

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> get() = _errorFlow

    init {
        fetchPosters()
    }

    fun fetchPosters() = viewModelScope.launch {
        getPostersUseCase(
            onStart = { _isLoading.value = true },
            onCompletion = { _isLoading.value = false },
            onError = { launch { _errorFlow.emit(it) } },
        ).collect {
            _posters.value = it
        }
    }
}
