package uz.alimov.effectivemobiletesttaskcourses.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
	@SerialName("courses")
	val courses: List<CourseDto>? = null
)