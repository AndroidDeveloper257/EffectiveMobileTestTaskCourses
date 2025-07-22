package uz.alimov.effectivemobiletesttaskcourses.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.alimov.effectivemobiletesttaskcourses.R
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData
import uz.alimov.effectivemobiletesttaskcourses.presentation.ui.theme.EffectiveMobileTestTaskCoursesTheme
import uz.alimov.effectivemobiletesttaskcourses.presentation.utils.formatDate

@Composable
fun CourseItem(
    modifier: Modifier = Modifier,
    course: CourseData,
    onBookmarkClicked: (Int) -> Unit,
    onCourseClicked: (Int) -> Unit
) {
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
        CourseDescription(
            text = course.text.orEmpty()
        )
        Spacer(Modifier.height(10.dp))
        CourseCostRow(
            price = course.price.orEmpty(),
            onCourseClicked = {
                onCourseClicked(course.id ?: 0)
            }
        )
    }
}

@Composable
fun CoverPhoto(
    modifier: Modifier = Modifier,
    course: CourseData,
    onBookmarkClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(
                course.coverResource ?: R.drawable.ic_launcher_foreground
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        FavoriteIcon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .size(36.dp),
            course = course,
            onClick = {
                onBookmarkClicked(course.id ?: 0)
            }
        )

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
                ratePoint = course.rate.orEmpty()
            )
            DateItem(course.publishDate.orEmpty())
        }
    }
}

@Composable
fun FavoriteIcon(
    modifier: Modifier = Modifier,
    course: CourseData,
    onClick: (Int) -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onClick(course.id ?: 0)
        }
    ) {
        Icon(
            painter = painterResource(
                if (course.hasLike == true) {
                    R.drawable.ic_favorite_selected
                } else {
                    R.drawable.ic_favorite_unselected
                }
            ),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun RateItem(
    modifier: Modifier = Modifier,
    ratePoint: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(R.drawable.ic_star),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            text = ratePoint,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DateItem(dateString: String) {
    Text(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        text = formatDate(dateString),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun CourseTitle(
    title: String,
    style: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = MaterialTheme.typography.titleMedium.fontSize
    )
) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        style = style
    )
}

@Composable
fun CourseDescription(
    text: String,
    style: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        fontSize = MaterialTheme.typography.bodySmall.fontSize
    ),
    maxLines: Int = 2,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = text,
        style = style,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun CourseCostRow(
    price: String,
    onCourseClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$price ${stringResource(R.string.ruble)}",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )
        TextButton(
            onClick = {
                onCourseClicked()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.details),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                )
                Spacer(Modifier.width(5.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun CourseItemPreview() {
    EffectiveMobileTestTaskCoursesTheme {
        CourseItem(
            modifier = Modifier.fillMaxWidth(),
            course = CourseData(
                rate = "4.8",
                price = "999",
                hasLike = true,
                publishDate = "2023-12-03",
                id = 1,
                text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании",
                title = "Java",
                startDate = "12.03.2023",
                coverResource = R.drawable.course_cover_1
            ),
            onBookmarkClicked = { },
            onCourseClicked = { }
        )
    }
}