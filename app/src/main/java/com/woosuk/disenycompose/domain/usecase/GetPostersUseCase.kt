package com.woosuk.disenycompose.domain.usecase

import com.woosuk.disenycompose.domain.model.Poster
import com.woosuk.disenycompose.domain.repository.DisneyPosterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostersUseCase @Inject constructor(
    private val disneyPosterRepository: DisneyPosterRepository,
) {
    suspend operator fun invoke(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
    ): Flow<List<Poster>> {
        return disneyPosterRepository.getDisneyPosters(
            onStart = onStart,
            onCompletion = onCompletion,
            onError = onError,
        )
    }
}
