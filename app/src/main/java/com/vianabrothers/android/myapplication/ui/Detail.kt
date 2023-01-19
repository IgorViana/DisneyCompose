package com.vianabrothers.android.myapplication.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vianabrothers.android.myapplication.model.DetailCharacterResponse
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Detail(onBackCLick: () -> Unit) {

    var isShowingBottomSheet by remember {
        mutableStateOf(false)
    }

    val viewModel: DetailViewModel = hiltViewModel()

    val characterDetail = viewModel.characterDetail.value

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val scope = rememberCoroutineScope()

    characterDetail?.let {

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                DetailBottomSheet()
            },
            sheetBackgroundColor = Color.Magenta,
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            CardDetail(characterDetail, onBackCLick) {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        isShowingBottomSheet = true
                        sheetState.expand()
                    } else {
                        isShowingBottomSheet = false
                        sheetState.collapse()
                    }
                }
            }
        }
    } ?: EmptyCardDetail()

}

@Composable
fun CardDetail(
    detail: DetailCharacterResponse,
    onBackCLick: () -> Unit,
    onImageClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(state = scrollState, orientation = Orientation.Horizontal),
        verticalArrangement = Arrangement.Top
    ) {
        CharacterMainImage(detail.imageUrl, onBackCLick, onImageClick)
        CharacterName(detail.name)
        CharacterListItem("Films", listOf("filme 1", "filme 2", "filme 2", "filme 2", "filme 2"))
        CharacterListItem("Short Films", detail.shortFilms)
        CharacterListItem("Tv Shows", detail.tvShows)
        CharacterListItem("Video games", detail.videoGames)
        CharacterListItem("Park attractions", detail.parkAttractions)
        CharacterListItem("Allies", detail.allies)
        CharacterListItem("Enemies", detail.enemies)
    }
}

@Composable
fun CharacterMainImage(
    url: String,
    onBackCLick: () -> Unit,
    onImageClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Gray,
                            Color.White,
                            Color.Transparent
                        )
                    )
                )
                .fillMaxWidth()
                .height(300.dp),
        )
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(36.dp))
                .background(Color.Gray)
                .clickable { onBackCLick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp)
                    .clip(RoundedCornerShape(250.dp))
                    .border(1.5.dp, Color.LightGray, CircleShape)
                    .clickable { onImageClick() }
                    .animateContentSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
    }
}


@Composable
fun DetailBottomSheet() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Teste")
    }
}

@Composable
fun CharacterName(name: String) {
    Text(
        text = name,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        fontSize = 30.sp
    )
}

@Composable
fun CharacterListItem(title: String, listItems: List<String>) {

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    if (listItems.isNotEmpty()) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(vertical = 8.dp)
        ) {
            CharacterInfoDetailTitle(isExpanded, title) {
                isExpanded = it
            }
        }
        if (isExpanded) {
            ListCharacterInfoDetails(listItems)
        }
    }
}

@Composable
fun CharacterInfoDetailTitle(isExpanded: Boolean, title: String, onExpandClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                onExpandClick(!isExpanded)
            }
    ) {
        Text(
            text = title
        )
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ListCharacterInfoDetails(listItems: List<String>) {
    LazyColumn() {
        items(listItems) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = ".", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = item)
            }

        }
    }
}

@Composable
fun EmptyCardDetail() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Empty Card"
        )
    }
}

@Preview(widthDp = 300, heightDp = 600, backgroundColor = 0xFF03A9F4)
@Composable
fun DetailImage() {
    CardDetail(
        detail = DetailCharacterResponse(
            1,
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            "Name",
            "Url",
            "https://static.wikia.nocookie.net/disney/images/5/51/Giffany.png"
        ),
        onBackCLick = {},
        onImageClick = {}
    )
}