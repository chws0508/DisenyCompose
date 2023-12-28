package com.woosuk.disenycompose.ui.posters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.woosuk.disenycompose.domain.model.Poster
import com.woosuk.disenycompose.ui.main.MainViewModel
import com.woosuk.disenycompose.ui.theme.DisenyComposeTheme

@Composable
fun RadioScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onPosterClick: (PosterId: Long) -> Unit,
) {
    val posters by mainViewModel.posters.collectAsStateWithLifecycle()
    RadioPosters(posters = posters, { onPosterClick(it) })
}

@Composable
fun RadioPosters(
    posters: List<Poster>,
    onPosterClick: (PosterId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    Column(modifier = modifier) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(17.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = posters,
            ) { poster ->
                RadioPoster(
                    poster = poster,
                    onPosterClick = { onPosterClick(it) },
                )
            }
        }
    }
}

@Composable
fun RadioPoster(
    poster: Poster,
    modifier: Modifier = Modifier,
    onPosterClick: (PosterId: Long) -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = { onPosterClick(poster.id) },
    ) {
        Row {
            AsyncImage(
                model = poster.poster,
                contentDescription = poster.description,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = poster.name,
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = poster.playtime, modifier = Modifier.padding(start = 10.dp, top = 5.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioPostersPreview() {
    DisenyComposeTheme(darkTheme = false) {
        RadioPosters(
            posters = List(10) { Poster.mock() },
            onPosterClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioPosterPreview() {
    DisenyComposeTheme(darkTheme = false) {
        RadioPoster(
            poster = Poster.mock(),
            onPosterClick = {},
        )
    }
}
