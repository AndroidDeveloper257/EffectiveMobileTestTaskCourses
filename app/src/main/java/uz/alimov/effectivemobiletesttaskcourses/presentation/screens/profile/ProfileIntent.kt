package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile

sealed interface ProfileIntent {
    data class CourseClicked(val id: Int) : ProfileIntent
    data class BookmarkClicked(val id: Int) : ProfileIntent
    object FetchCourses : ProfileIntent
    object NavigateHomeClicked : ProfileIntent
    object LogoutClicked : ProfileIntent
}