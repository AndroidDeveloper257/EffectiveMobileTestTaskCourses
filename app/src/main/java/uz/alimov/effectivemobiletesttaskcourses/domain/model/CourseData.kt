package uz.alimov.effectivemobiletesttaskcourses.domain.model

data class CourseData(
    val rate: String? = null,
    val price: String? = null,
    val hasLike: Boolean? = null,
    val publishDate: String? = null,
    val id: Int? = null,
    val text: String? = null,
    val title: String? = null,
    val startDate: String? = null,
    val coverResource: Int? = null,
    val hasStarted: Boolean? = null
)