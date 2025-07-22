package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites

sealed interface FavoritesIntent {
    data class CourseClicked(val id: Int) : FavoritesIntent
    data class BookmarkClicked(val id: Int) : FavoritesIntent
    object FetchCourses : FavoritesIntent
    object NavigateHomeClicked : FavoritesIntent
}