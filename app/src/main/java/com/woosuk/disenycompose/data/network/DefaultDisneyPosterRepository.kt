package com.woosuk.disenycompose.data.network

import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.woosuk.disenycompose.data.network.dto.toEntity
import com.woosuk.disenycompose.data.network.dto.toPoster
import com.woosuk.disenycompose.data.persistence.PosterDao
import com.woosuk.disenycompose.data.persistence.entitiy.toPoster
import com.woosuk.disenycompose.domain.model.Poster
import com.woosuk.disenycompose.domain.repository.DisneyPosterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultDisneyPosterRepository @Inject constructor(
    private val disneyService: DisenyService,
    private val posterDao: PosterDao,
) : DisneyPosterRepository {

    override suspend fun getDisneyPoster(id: Long): Poster = withContext(Dispatchers.IO) {
        posterDao.getPoster(id)?.toPoster() ?: Poster.mock()
    }

    override fun getDisneyPosters(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
    ): Flow<List<Poster>> = flow {
        val posters: List<Poster> = posterDao.getPosters().map { it.toPoster() }
        if (posters.isEmpty()) {
            disneyService.getDisneyPosters()
                .suspendOnSuccess {
                    posterDao.savePosters(data.map { it.toEntity() })
                    emit(data.map { it.toPoster() })
                }
                .onFailure { onError(this.message()) }
        } else {
            emit(posters)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}
