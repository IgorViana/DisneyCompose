package com.vianabrothers.android.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
        ) {
            SearchBar()
            Button(onClick = { viewModel.loadCharactersList() }) {
                Text(text = "Reload")
            }
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
            .padding(16.dp),
        singleLine = true,
        trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) }
    )
    /*Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
    }*/

}

@Composable
fun CharactersList(
    data: List<DisneyCharactersResponse>,
    onCharacterSelected: (url: String) -> Unit
) {
    LazyColumn()
    {
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
