package uz.alimov.effectivemobiletesttaskcourses.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = background_dark,
    onBackground = onBackground_dark,
    primary = primary_dark,
    onPrimary = onPrimary_dark,
    surface = surface_dark,
    onSurface = onSurface_dark,
    primaryContainer = primaryContainer_dark,
    secondaryContainer = secondaryContainer_dark,
    onPrimaryContainer = onPrimaryContainer_dark,
    onSecondaryContainer = onSecondaryContainer_dark,
    scrim = scrim_dark
)

@Composable
fun EffectiveMobileTestTaskCoursesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}