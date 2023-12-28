package com.woosuk.disenycompose.domain.repository

import com.woosuk.disenycompose.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface DisneyPosterRepository {

    suspend fun getDisneyPoster(id: Long): Poster

    fun getDisneyPosters(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
    ): Flow<List<Poster>>
}
