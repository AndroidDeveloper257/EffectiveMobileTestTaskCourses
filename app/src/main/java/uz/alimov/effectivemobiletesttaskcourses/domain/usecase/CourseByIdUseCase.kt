package uz.alimov.effectivemobiletesttaskcourses.domain.usecase

import uz.alimov.effectivemobiletesttaskcourses.domain.repository.CoursesRepository

class CourseByIdUseCase(
    private val repository: CoursesRepository
) {
    suspend fun invoke(courseId: Int) = repository.getCourseById(courseId)
}