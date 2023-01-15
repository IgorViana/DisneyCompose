package com.vianabrothers.android.myapplication.repository

import com.vianabrothers.android.myapplication.network.DisneyService


class DisneyRepository(private val disneyService: DisneyService) : IDisneyRepository {

    override suspend fun getDisneyCharacters() = disneyService.listCharacters()

    override suspend fun getDisneyCharactersDetail(id: String) = disneyService.characterDetails(id)

}