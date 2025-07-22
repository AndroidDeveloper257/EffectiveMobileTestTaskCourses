package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.details

import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData

data class DetailsState(
    val course: CourseData = CourseData(),
    val isLoading: Boolean = true
)