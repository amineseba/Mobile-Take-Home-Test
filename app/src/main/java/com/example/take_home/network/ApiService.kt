package com.example.take_home.network

import com.example.take_home.data.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ) : CharacterResponse
}
