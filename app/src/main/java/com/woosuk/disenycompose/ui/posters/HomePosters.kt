package com.woosuk.disenycompose.ui.posters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onPosterClick: (Long) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val posters by mainViewModel.posters.collectAsStateWithLifecycle()
    val isLoading by mainViewModel.isLoading.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp).align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary,
            )
        } else {
            HomePosters(posters = posters, { onPosterClick(it) })
        }
        LaunchedEffect(Unit) {
            mainViewModel.errorFlow
                .onEach {
                    snackbarHostState.showSnackbar(message = it)
                }.launchIn(this)
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun HomePosters(
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
                    HomePoster(
                        poster = poster,
                        onPosterClick = { onPosterClick(it) },
                    )
                },
            )
        }
    }
}

@Composable
fun HomePoster(
    poster: Poster,
    modifier: Modifier = Modifier,
    onPosterClick: (PosterId: Long) -> Unit,
) {
    Surface(
        modifier = modifier.wrapContentSize(),
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
                    .fillMaxWidth()
                    .height(200.dp),
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
fun HomePosterPreview() {
    DisenyComposeTheme {
        HomePoster(poster = Poster.mock(), onPosterClick = {})
    }
}

@Preview(name = "HomePosters Preview")
@Composable
fun HomePostersPreview() {
    DisenyComposeTheme {
        HomePosters(posters = List(10) { Poster.mock() }, onPosterClick = {}, modifier = Modifier)
    }
}

private const val CELL_COUNT = 2
