package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.R
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.DateItem
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.RateItem

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(),
    courseId: Int,
    innerPadding: PaddingValues,
    navigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(courseId) {
        viewModel.onIntent(DetailsIntent.LoadCourse(courseId))
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(
                        state.value.course.coverResource ?: R.drawable.ic_launcher_foreground
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            start = 20.dp,
                            end = 20.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground),
                        onClick = navigateBack
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_navigate_back),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground),
                        onClick = {
                            viewModel.onIntent(DetailsIntent.ToggleBookmarkClicked)
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (state.value.course.hasLike == true) R.drawable.ic_favorite_selected
                                else R.drawable.ic_favorite_unselected
                            ),
                            contentDescription = null,
                            tint = if (state.value.course.hasLike == true) Color.Unspecified else MaterialTheme.colorScheme.background
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RateItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        ratePoint = state.value.course.rate.orEmpty()
                    )
                    DateItem(state.value.course.publishDate.orEmpty())
                }
            }
        }
        item {
            CourseDetails(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                course = state.value.course,
                onStartCourseClicked = {
                    viewModel.onIntent(DetailsIntent.StartCourseClicked)
                }
            )
        }
    }
}

@Composable
fun CourseDetails(
    modifier: Modifier = Modifier,
    course: CourseData,
    onStartCourseClicked: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = course.title.orEmpty(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                painter = painterResource(R.drawable.course_author),
                contentDescription = null
            )
            Column {
                Text(
                    text = stringResource(R.string.author),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = stringResource(R.string.author_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(Modifier.height(25.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onStartCourseClicked()
            },
            enabled = course.hasStarted == false,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.start_course),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.open_platform),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.about_course),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = course.text.orEmpty(),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}