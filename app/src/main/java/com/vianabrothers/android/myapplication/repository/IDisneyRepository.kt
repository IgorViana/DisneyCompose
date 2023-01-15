package com.vianabrothers.android.myapplication.repository

import com.vianabrothers.android.myapplication.model.DetailCharacterResponse
import com.vianabrothers.android.myapplication.model.DisneyCharactersListResponse

interface IDisneyRepository {
    suspend fun getDisneyCharacters(): DisneyCharactersListResponse
    suspend fun getDisneyCharactersDetail(id: String): DetailCharacterResponse
}