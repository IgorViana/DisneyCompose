package com.vianabrothers.android.myapplication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailCharacterResponse(
    @Json(name = "_id")
    val id: Int,
    val films: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val videoGames: List<String>,
    val parkAttractions: List<String>,
    val allies: List<String>,
    val enemies: List<String>,
    val name: String,
    val imageUrl: String,
    val url: String
)

/*
{"_id":7,
"films":[],
"shortFilms":[],
"tvShows":["Gravity Falls"],
"videoGames":[],
"parkAttractions":[],
"allies":[],
"enemies":[],
"name":".GIFfany",
"imageUrl":"https://static.wikia.nocookie.net/disney/images/5/51/Giffany.png",
"url":"https://api.disneyapi.dev/characters/7"
}
 */
