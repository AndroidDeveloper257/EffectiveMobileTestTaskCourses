package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.R
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.CourseItem
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.EmptyListMessage
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.StickyHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel(),
    onCourseClicked: (Int) -> Unit,
    onNavigateHomeClicked: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FavoritesEffect.NavigateToDetails -> {
                    onCourseClicked(effect.id)
                }

                FavoritesEffect.NavigateToHome -> {
                    onNavigateHomeClicked()
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stickyHeader {
            StickyHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                title = R.string.favorite
            )
        }
        if (state.value.isEmpty) {
            item {
                EmptyListMessage(
                    modifier = Modifier.fillParentMaxSize(),
                    message = R.string.favorites_empty,
                    tryAgainButton = {
                        Button(
                            onClick = {
                                viewModel.onIntent(FavoritesIntent.NavigateHomeClicked)
                            }
                        ) {
                            Text(text = stringResource(R.string.open_home))
                        }
                    }
                )
            }
        } else {
            items(
                items = state.value.savedCourses,
                key = { it.id ?: 0 }
            ) {
                CourseItem(
                    modifier = Modifier.fillParentMaxWidth(),
                    course = it,
                    onBookmarkClicked = { id ->
                        viewModel.onIntent(FavoritesIntent.BookmarkClicked(id))
                    },
                    onCourseClicked = { id ->
                        viewModel.onIntent(FavoritesIntent.CourseClicked(id))
                    }
                )
            }
        }
    }

    /*Column(
        modifier = modifier
    ) {
        Spacer(Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 30.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (state.value.isEmpty) {
                item {
                    EmptyListMessage(
                        modifier = Modifier.fillParentMaxSize(),
                        message = R.string.favorites_empty,
                        tryAgainButton = {
                            Button(
                                onClick = {
                                    viewModel.onIntent(FavoritesIntent.NavigateHomeClicked)
                                }
                            ) {
                                Text(text = stringResource(R.string.open_home))
                            }
                        }
                    )
                }
            } else {
                items(
                    items = state.value.savedCourses,
                    key = { it.id ?: 0 }
                ) {
                    CourseItem(
                        modifier = Modifier.fillParentMaxWidth(),
                        course = it,
                        onBookmarkClicked = { id ->
                            viewModel.onIntent(FavoritesIntent.BookmarkClicked(id))
                        },
                        onCourseClicked = { id ->
                            viewModel.onIntent(FavoritesIntent.CourseClicked(id))
                        }
                    )
                }
            }
        }
    }*/
}