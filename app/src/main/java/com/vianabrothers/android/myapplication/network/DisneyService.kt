package com.vianabrothers.android.myapplication.network

import com.vianabrothers.android.myapplication.model.DetailCharacterResponse
import com.vianabrothers.android.myapplication.model.DisneyCharactersListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DisneyService {

    @GET("/characters")
    suspend fun listCharacters(): DisneyCharactersListResponse

    @GET("characters/{id}")
    suspend fun characterDetails(@Path("id") id: String): DetailCharacterResponse
}