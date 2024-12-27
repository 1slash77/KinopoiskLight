package com.pablok.kinopoisklight.actor


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.ActorDetails
import com.pablok.kinopoisklight.ui.components.ErrorContent
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.elements.TopAppBarFavorites
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import java.text.SimpleDateFormat

@Composable
fun ActorScreen(
    id: String?,
    viewModel: ActorViewModel = hiltViewModel()
) {
    val state by viewModel.screenState

    Log.d("mytag", "ActorScreen() ${state.isRefreshing}, ${state.actor}")

    val onRefresh: () -> Unit = {
        viewModel.fetch(id)
    }

    if (state.actor == null) {
        LaunchedEffect(Unit) {
            onRefresh()
        }
    }

    ActorDetails(state.actor,
        isRefreshing = state.isRefreshing,
        onFavoriteChanged = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetails(
    actor: ActorDetails?,
    isRefreshing: Boolean = false,
    errorMessage: String? = null,

    onFavoriteChanged: (Boolean) -> Unit = {},
) {
    val isFavorite = actor?.isFavorite ?: false
    Scaffold(
        topBar = {
            TopAppBarFavorites(
                title = actor?.name ?: "",
                showOnlyFavorites = isFavorite,
                onClickFavorites = { onFavoriteChanged(!isFavorite) }
            )
        }
    ) { contentPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Red)
                .padding(contentPadding),
            isRefreshing = isRefreshing,
            onRefresh = { },
        ) {
            if (!isRefreshing) {
                if (errorMessage != null) {
                    ErrorContent(errorMessage, onClick = {})
                } else if (actor != null) {
                    ActorContent(actor)
                }
            }
        }
    }

}

@Composable
fun ActorContent(
    actor: ActorDetails,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
            /*                    .clip(
                                    MaterialTheme.shapes.medium.copy(
                                        bottomEnd = CornerSize(0.dp),
                                        bottomStart = CornerSize(0.dp)
                                    )
                                )*/
            ,
            model = actor.photoUrl,
            placeholder = debugPlaceholder(),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(16.dp))
        Text("${actor.name}",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(Modifier.height(16.dp))
        Text("День рождения: ${actor.birhtday?.let { SimpleDateFormat("dd MMM yyyy").format(it) }}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        Text(
            actor.profession.joinToString(", "),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        Text("Факты",
            style = MaterialTheme.typography.bodyLarge
        )
        actor.facts.forEach {
            Spacer(Modifier.height(8.dp))
            Text("- $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

@Preview
@Composable
fun ActorDetailsPreview() {
    KinopoiskLightTheme {
        ActorDetails(MockEntities.mockActorDetails())
    }
}