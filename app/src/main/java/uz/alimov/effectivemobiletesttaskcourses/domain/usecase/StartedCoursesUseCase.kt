package uz.alimov.effectivemobiletesttaskcourses.domain.usecase

import uz.alimov.effectivemobiletesttaskcourses.domain.repository.CoursesRepository

class StartedCoursesUseCase(
    private val repository: CoursesRepository
) {
    fun invoke() = repository.getStartedCourses()
}