package com.woosuk.disenycompose.data.network

import com.skydoves.sandwich.ApiResponse
import com.woosuk.disenycompose.data.network.dto.PosterDto
import retrofit2.http.GET

interface DisenyService {
    @GET("DisneyPosters2.json")
    suspend fun getDisneyPosters(): ApiResponse<List<PosterDto>>
}
