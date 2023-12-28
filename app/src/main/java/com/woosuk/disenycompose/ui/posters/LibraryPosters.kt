package com.woosuk.disenycompose.ui.posters

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.woosuk.disenycompose.domain.model.Poster
import com.woosuk.disenycompose.ui.main.MainViewModel
import com.woosuk.disenycompose.ui.theme.DisenyComposeTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LibraryScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onPosterClick: (Long) -> Unit,
) {
    val context = LocalContext.current
    val posters by mainViewModel.posters.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        mainViewModel.errorFlow
            .onEach { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
            .launchIn(this)
    }
    LibraryPosters(posters = posters, onPosterClick = { onPosterClick(it) })
}

@Composable
fun LibraryPosters(
    posters: List<Poster>,
    onPosterClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyGridState()
    Column(modifier = modifier) {
        LazyVerticalGrid(
            state = listState,
            contentPadding = PaddingValues(17.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            columns = GridCells.Fixed(CELL_COUNT),
        ) {
            items(
                items = posters,
                itemContent = { poster ->
                    LibraryPoster(
                        poster = poster,
                        onPosterClick = { onPosterClick(it) },
                    )
                },
            )
        }
    }
}

@Composable
fun LibraryPoster(
    poster: Poster,
    modifier: Modifier = Modifier,
    onPosterClick: (PosterId: Long) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = { onPosterClick(poster.id) },
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = poster.poster,
                contentDescription = poster.description,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = poster.name,
                modifier = Modifier,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = poster.playtime,
                modifier = Modifier.padding(top = 5.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "HomePoster Preview")
@Composable
fun LibraryPosterPreview() {
    DisenyComposeTheme {
        LibraryPoster(poster = Poster.mock(), onPosterClick = {})
    }
}

@Preview(name = "HomePosters Preview")
@Composable
fun LibraryPostersPreview() {
    DisenyComposeTheme {
        LibraryPosters(
            posters = List(10) { Poster.mock() },
            onPosterClick = {},
            modifier = Modifier,
        )
    }
}

private const val CELL_COUNT = 2
