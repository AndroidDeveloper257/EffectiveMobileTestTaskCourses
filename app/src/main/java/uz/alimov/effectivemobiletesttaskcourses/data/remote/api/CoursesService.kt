package uz.alimov.effectivemobiletesttaskcourses.data.remote.api

import uz.alimov.effectivemobiletesttaskcourses.data.remote.model.CoursesResponse

interface CoursesService {

    suspend fun downloadCourses(): CoursesResponse

}