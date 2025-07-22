package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites

import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData

data class FavoritesState(
    val savedCourses: List<CourseData> = emptyList()
) {
    val isEmpty get() = savedCourses.isEmpty()
}