package com.vianabrothers.android.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vianabrothers.android.myapplication.model.DisneyCharactersResponse

@Composable
fun Home(onCharacterSelected: (url: String) -> Unit) {

    val viewModel: HomeViewModel = hiltViewModel()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Disney App") }
            )
        }) {
        Column(
            Modifier
                .fillMaxSize()
                .scrollable(state = scrollState, orientation = Orientation.Vertical)
                .padding(horizontal = 16.dp)
        ) {
            SearchBar()
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(color = Color.Gray)
                    .height(2.dp)
                    .fillMaxWidth()
            )
            val list = viewModel.charactersList.value
            CharactersList(list, onCharacterSelected)
        }
    }
}

@Composable
fun SearchBar() {
    val textChanged = remember {
        mutableStateOf("Pesquisar")
    }
    TextField(
        value = textChanged.value,
        onValueChange = { text -> textChanged.value = text },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        singleLine = true,
        trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) }
    )

}

@Composable
fun CharactersList(
    data: List<DisneyCharactersResponse>,
    onCharacterSelected: (url: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data) { character ->
            CharacterItem(character, onCharacterSelected)
        }
    }
}

@Composable
fun CharacterItem(character: DisneyCharactersResponse, onCharacterSelected: (url: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(Color.LightGray)
            .clickable { onCharacterSelected(character.id.toString()) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = character.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(width = 70.dp, height = 70.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Text(
            text = character.name,
            modifier = Modifier.padding(horizontal = 16.dp),
            maxLines = 2
        )
    }

}

@Preview(widthDp = 300, heightDp = 600, backgroundColor = 0xFF03A9F4)
@Composable
fun HomePreview() {
    CharactersList(
        data = listOf(
            DisneyCharactersResponse(
                id = 2L,
                name = "Name",
                imageUrl = "",
                url = "",
                films = emptyList(),
                shortFilms = emptyList(),
                tvShows = emptyList(),
                videoGames = emptyList(),
                parkAttractions = emptyList(),
                allies = emptyList(),
                enemies = emptyList()

            )
        )
    ) {}
}
