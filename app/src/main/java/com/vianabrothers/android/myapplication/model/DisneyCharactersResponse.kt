package com.vianabrothers.android.myapplication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DisneyCharactersResponse(
    @Json(name = "_id")
    val id: Long,
    val name: String,
    val imageUrl: String,
    val url: String,
    val films: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val videoGames: List<String>,
    val parkAttractions: List<String>,
    val allies: List<String>,
    val enemies: List<String>

)

@JsonClass(generateAdapter = true)
data class DisneyCharactersListResponse(
    @Json(name = "data") val data: List<DisneyCharactersResponse>
)
