package uz.alimov.effectivemobiletesttaskcourses.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickyHeader(
    modifier: Modifier = Modifier,
    title: Int
) {
    Box(
        modifier = modifier
            .height(TopAppBarDefaults.LargeAppBarCollapsedHeight)
            .padding(bottom = 15.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text = stringResource(title),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )
    }
}