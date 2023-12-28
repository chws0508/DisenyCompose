package com.woosuk.disenycompose.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.disenycompose.domain.model.Poster
import com.woosuk.disenycompose.domain.repository.DisneyPosterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val posterRepository: DisneyPosterRepository,
) : ViewModel() {

    private val _poster: MutableStateFlow<Poster> = MutableStateFlow(Poster.mock())
    val poster: StateFlow<Poster> = _poster

    fun fetchPoster(id: Long) {
        viewModelScope.launch {
            _poster.value = posterRepository.getDisneyPoster(id)
        }
    }
}
