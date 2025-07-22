package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home

import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData

data class HomeState(
    val courseList: List<CourseData> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = ""
) {
    val isEmpty = courseList.isEmpty()
}