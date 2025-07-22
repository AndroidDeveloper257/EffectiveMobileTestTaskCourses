package uz.alimov.effectivemobiletesttaskcourses.domain.usecase

import uz.alimov.effectivemobiletesttaskcourses.domain.repository.CoursesRepository

class GetCoursesUseCase(
    private val repository: CoursesRepository
) {
    fun invoke() = repository.getCourses()
}