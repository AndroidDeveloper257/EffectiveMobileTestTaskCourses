package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home

sealed interface HomeIntent {
    data class CourseClicked(val id: Int) : HomeIntent
    data class BookmarkClicked(val id: Int) : HomeIntent
    object SortClicked : HomeIntent
    object LoadCourses : HomeIntent
}