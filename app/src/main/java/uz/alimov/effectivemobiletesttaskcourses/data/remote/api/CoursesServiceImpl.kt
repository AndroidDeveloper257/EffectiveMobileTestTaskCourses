package uz.alimov.effectivemobiletesttaskcourses.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import uz.alimov.effectivemobiletesttaskcourses.BuildConfig
import uz.alimov.effectivemobiletesttaskcourses.data.remote.model.CoursesResponse

class CoursesServiceImpl(
    private val client: HttpClient
): CoursesService {

    override suspend fun downloadCourses(): CoursesResponse {
        val jsonText = client.get(BuildConfig.URL) {
            timeout {
                requestTimeoutMillis = 15000
            }
        }.bodyAsText()
        return Json.decodeFromString<CoursesResponse>(jsonText)
    }
}