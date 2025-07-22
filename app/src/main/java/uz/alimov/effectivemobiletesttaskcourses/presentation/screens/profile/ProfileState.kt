package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile

import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData

data class ProfileState(
    val courses: List<CourseData> = emptyList(),
) {
    val isEmpty get() = courses.isEmpty()
}