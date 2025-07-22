package uz.alimov.effectivemobiletesttaskcourses.presentation.navigation

import kotlinx.serialization.Serializable
import uz.alimov.effectivemobiletesttaskcourses.R

sealed interface Destinations {
    @Serializable
    object Login : Destinations

    @Serializable
    object Home : Destinations

    @Serializable
    data class CourseDetails(val courseId: Int) : Destinations

    @Serializable
    object Favorites : Destinations

    @Serializable
    object Profile : Destinations
}

enum class TopLevelDestinations(
    val label: Int,
    val icon: Int,
    val route: Destinations
) {
    Home(
        label = R.string.home,
        icon = R.drawable.ic_home,
        route = Destinations.Home
    ),
    Favorites(
        label = R.string.favorite,
        icon = R.drawable.ic_favorite,
        route = Destinations.Favorites
    ),
    Profile(
        label = R.string.account,
        icon = R.drawable.ic_profile,
        route = Destinations.Profile
    )
}