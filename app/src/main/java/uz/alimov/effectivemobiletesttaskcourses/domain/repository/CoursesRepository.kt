package uz.alimov.effectivemobiletesttaskcourses.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.alimov.effectivemobiletesttaskcourses.domain.Resource
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData

interface CoursesRepository {

    fun getCourses(): Flow<Resource<List<CourseData>, String>>

    suspend fun toggleBookmarkCourse(id: Int)

    fun getCourseById(id: Int): Flow<CourseData>

    fun getBookmarkedCourses(): Flow<List<CourseData>>

    fun getStartedCourses(): Flow<List<CourseData>>

    suspend fun startCourse(courseId: Int)
}