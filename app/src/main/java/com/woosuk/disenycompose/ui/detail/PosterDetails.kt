package com.woosuk.disenycompose.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.woosuk.disenycompose.domain.model.Poster

@Composable
fun PosterDetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    onPressBack: () -> Unit,
    id: Long,
) {
    val poster = detailsViewModel.poster.collectAsStateWithLifecycle().value

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        PosterDetails(poster = poster, { onPressBack() })
    }

    detailsViewModel.fetchPoster(id)
}

@Composable
fun PosterDetails(
    poster: Poster,
    onPressBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box {
            AsyncImage(
                model = poster.poster,
                contentDescription = poster.description,
                modifier = Modifier.fillMaxWidth().height(500.dp),
                contentScale = ContentScale.FillBounds,
            )

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable { onPressBack() },
                tint = Color.White,
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            style = MaterialTheme.typography.h4,
            text = poster.name,
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp),
            style = MaterialTheme.typography.body1,
            text = poster.description,
        )
    }
}

@Preview
@Composable
fun PosterDetailsPreview() {
    PosterDetails(poster = Poster.mock(), onPressBack = {})
}
