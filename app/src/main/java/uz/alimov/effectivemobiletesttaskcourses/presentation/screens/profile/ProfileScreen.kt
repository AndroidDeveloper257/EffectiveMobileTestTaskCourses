package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.R
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.CourseTitle
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.CoverPhoto
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.EmptyListMessage
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.StickyHeader

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    onNavigateToDetails: (Int) -> Unit,
    onNavigateHome: () -> Unit,
    onLogout: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileEffect.Logout -> {
                    onLogout()
                }

                ProfileEffect.NavigateHome -> {
                    onNavigateHome()
                }

                is ProfileEffect.NavigateToDetails -> {
                    onNavigateToDetails(effect.id)
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 20.dp, end = 20.dp, bottom = 30.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stickyHeader {
            StickyHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                title = R.string.profile
            )
        }
        item {
            ProfileHeader(
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.onIntent(ProfileIntent.LogoutClicked)
            }
        }
        stickyHeader {
            StickyHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                title = R.string.your_courses
            )
        }
        if (state.value.isEmpty) {
            item {
                EmptyListMessage(
                    modifier = Modifier.fillParentMaxSize(),
                    message = R.string.started_courses_empty,
                    tryAgainButton = {
                        Button(
                            onClick = {
                                viewModel.onIntent(ProfileIntent.NavigateHomeClicked)
                            }
                        ) {
                            Text(text = stringResource(R.string.open_home))
                        }
                    }
                )
            }
        } else {
            items(
                items = state.value.courses,
                key = { it.id ?: 0 }
            ) {
                StartedCourseItem(
                    modifier = Modifier.fillMaxWidth(),
                    course = it,
                    onBookmarkClicked = { id ->
                        viewModel.onIntent(ProfileIntent.BookmarkClicked(id))
                    },
                    onCourseClicked = { id ->
                        viewModel.onIntent(ProfileIntent.CourseClicked(id))
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        ProfileHeaderItem(
            modifier = Modifier.fillMaxWidth(),
            text = R.string.contact_support
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        ProfileHeaderItem(
            modifier = Modifier.fillMaxWidth(),
            text = R.string.settings
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        ProfileHeaderItem(
            modifier = Modifier.fillMaxWidth(),
            text = R.string.logout,
            onClick = onLogout
        )
    }
}

@Composable
fun ProfileHeaderItem(
    modifier: Modifier = Modifier,
    text: Int,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 15.dp),
            text = stringResource(text),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = TextStyle(
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        )
        Icon(
            modifier = Modifier.padding(end = 10.dp),
            painter = painterResource(R.drawable.ic_keyboard_arrow_right),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun StartedCourseItem(
    modifier: Modifier = Modifier,
    course: CourseData,
    onBookmarkClicked: (Int) -> Unit,
    onCourseClicked: (Int) -> Unit
) {
    val finished = (5..25).random()
    val total = (finished + 10..40).random()
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                onCourseClicked(course.id ?: 0)
            }
    ) {
        CoverPhoto(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            course = course,
            onBookmarkClicked = {
                onBookmarkClicked(course.id ?: 0)
            }
        )
        Spacer(Modifier.height(20.dp))
        CourseTitle(
            title = course.title.orEmpty()
        )
        Spacer(Modifier.height(10.dp))
        CourseProgress(
            finished = finished,
            total = total
        )
    }
}

@Composable
fun CourseProgress(
    finished: Int = 22,
    total: Int = 44,
    finishedColor: Color = MaterialTheme.colorScheme.primary,
    remainingColor: Color = MaterialTheme.colorScheme.surface,
    height: Dp = 8.dp
) {
    val progress = try {
        finished / total.toFloat()
    } catch (_: Exception) {
        0f
    }
    val lessonStatus = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(finished.toString())
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        ) {
            append("/$total ${stringResource(R.string.lessons)}")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${(finished * 100) / total}%",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            )
            Text(
                text = lessonStatus,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(height / 2))
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(height / 2))
                    .background(finishedColor)
            )
            Box(
                modifier = Modifier
                    .weight((1f - progress).coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(height / 2))
                    .background(remainingColor)
            )
        }
    }
}