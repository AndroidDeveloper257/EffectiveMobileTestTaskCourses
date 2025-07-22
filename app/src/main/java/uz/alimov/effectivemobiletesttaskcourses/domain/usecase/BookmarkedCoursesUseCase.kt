package uz.alimov.effectivemobiletesttaskcourses.domain.usecase

import uz.alimov.effectivemobiletesttaskcourses.domain.repository.CoursesRepository

class BookmarkedCoursesUseCase(
    private val repository: CoursesRepository
) {
    fun invoke() = repository.getBookmarkedCourses()
}