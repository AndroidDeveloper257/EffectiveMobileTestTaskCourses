package uz.alimov.effectivemobiletesttaskcourses.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(

	@SerialName("rate")
	val rate: String? = null,

	@SerialName("price")
	val price: String? = null,

	@SerialName("hasLike")
	val hasLike: Boolean? = null,

	@SerialName("publishDate")
	val publishDate: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("text")
	val text: String? = null,

	@SerialName("title")
	val title: String? = null,

	@SerialName("startDate")
	val startDate: String? = null
)