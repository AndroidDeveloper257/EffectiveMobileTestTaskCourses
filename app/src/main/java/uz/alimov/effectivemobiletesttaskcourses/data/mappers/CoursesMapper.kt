package uz.alimov.effectivemobiletesttaskcourses.data.mappers

import uz.alimov.effectivemobiletesttaskcourses.data.local.entity.CourseEntity
import uz.alimov.effectivemobiletesttaskcourses.data.remote.model.CourseDto
import uz.alimov.effectivemobiletesttaskcourses.data.util.ConstValues.COURSE_COVER_LIST
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData

fun CourseEntity.toCourseData(): CourseData {
    return CourseData(
        rate = rate,
        price = price,
        hasLike = hasLike,
        publishDate = publishDate,
        id = id,
        text = text,
        title = title,
        startDate = startDate,
        coverResource = coverResource,
        hasStarted = hasStarted
    )
}

fun CourseDto.toEntity(index: Int): CourseEntity {
    return CourseEntity(
        rate = rate.toString(),
        price = price.toString(),
        hasLike = hasLike == true,
        publishDate = publishDate.toString(),
        id = id ?: 0,
        text = text.toString(),
        title = title.toString(),
        startDate = startDate.toString(),
        coverResource = COURSE_COVER_LIST[index % COURSE_COVER_LIST.size],
        hasStarted = false
    )
}