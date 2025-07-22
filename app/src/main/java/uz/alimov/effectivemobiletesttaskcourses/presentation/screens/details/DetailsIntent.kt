package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.details

sealed interface DetailsIntent {
    data class LoadCourse(val id: Int) : DetailsIntent
    object ToggleBookmarkClicked : DetailsIntent
    object StartCourseClicked : DetailsIntent
}