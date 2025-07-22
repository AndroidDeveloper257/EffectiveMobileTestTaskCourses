package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.R
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.CourseItem
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.EmptyListMessage
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.ProgressBar
import uz.alimov.effectivemobiletesttaskcourses.presentation.utils.ConstValues.NO_INTERNET

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onCourseClicked: (Int) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetails -> {
                    onCourseClicked(effect.id)
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 20.dp, end = 20.dp, bottom = 10.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stickyHeader {
            HomeHeader(
                onSortClick = {
                    viewModel.onIntent(HomeIntent.SortClicked)
                }
            )
        }

        if (state.value.isLoading) {
            item {
                ProgressBar(modifier = Modifier.fillParentMaxSize())
            }
        } else if (state.value.isEmpty) {
            item {
                EmptyListMessage(
                    modifier = Modifier.fillParentMaxSize(),
                    message = if (state.value.error == NO_INTERNET) R.string.no_internet else R.string.failed,
                    tryAgainButton = {
                        Button(
                            onClick = {
                                viewModel.onIntent(HomeIntent.LoadCourses)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.try_again)
                            )
                        }
                    }
                )
            }
        } else {
            items(
                items = state.value.courseList,
                key = { it.id ?: 0 }
            ) {
                CourseItem(
                    modifier = Modifier.fillParentMaxWidth(),
                    course = it,
                    onBookmarkClicked = { id ->
                        viewModel.onIntent(HomeIntent.BookmarkClicked(id))
                    },
                    onCourseClicked = { id ->
                        viewModel.onIntent(HomeIntent.CourseClicked(id))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(
    onSortClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                modifier = Modifier
                    .weight(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        onSortClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_filter),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
        TextButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {}
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(R.string.publish_date),
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    painter = painterResource(R.drawable.ic_sort),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var searchQuery by remember {
        mutableStateOf("")
    }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(30.dp))
            .background(
                color = MaterialTheme.colorScheme.surface
            ),
        value = searchQuery,
        onValueChange = {
            searchQuery = it
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search_courses),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .width(60.dp)
                    .background(Color.Transparent),
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    )
}