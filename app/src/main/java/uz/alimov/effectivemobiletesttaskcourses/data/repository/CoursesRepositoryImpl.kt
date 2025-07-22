package uz.alimov.effectivemobiletesttaskcourses.data.repository

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.alimov.effectivemobiletesttaskcourses.data.local.dao.CoursesDao
import uz.alimov.effectivemobiletesttaskcourses.data.mappers.toCourseData
import uz.alimov.effectivemobiletesttaskcourses.data.mappers.toEntity
import uz.alimov.effectivemobiletesttaskcourses.data.remote.api.CoursesService
import uz.alimov.effectivemobiletesttaskcourses.domain.Resource
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData
import uz.alimov.effectivemobiletesttaskcourses.domain.repository.CoursesRepository
import uz.alimov.effectivemobiletesttaskcourses.presentation.utils.ConstValues.NO_INTERNET

class CoursesRepositoryImpl(
    private val service: CoursesService,
    private val dao: CoursesDao
) : CoursesRepository {

    override fun getCourses(): Flow<Resource<List<CourseData>, String>> = flow {
        val isEmpty = withContext(Dispatchers.IO) {
            dao.isEmpty()
        }
        if (isEmpty) {
            try {
                val response = service.downloadCourses()
                val entities = response.courses?.mapIndexed { index, courseDto ->
                    courseDto.toEntity(index)
                } ?: emptyList()
                dao.insertCourses(entities)
                emit(
                    Resource.Success(
                        data = entities.map {
                            it.toCourseData()
                        }
                    )
                )
                return@flow
            } catch (e: Exception) {
                when (e) {
                    is HttpRequestTimeoutException, is ConnectTimeoutException, is SocketTimeoutException -> {
                        emit(Resource.Error(rawResponse = NO_INTERNET))
                    }

                    else -> {
                        emit(
                            Resource.Error(
                                rawResponse = "Something went wrong"
                            )
                        )
                    }
                }
            }
        }
        emitAll(
            dao.getCourses().map { entities ->
                Resource.Success(
                    data = entities.map { it.toCourseData() }
                )
            }
        )
    }

    override suspend fun toggleBookmarkCourse(id: Int) {
        withContext(Dispatchers.IO) {
            if (dao.isCourseBookmarked(id)) {
                dao.unBookmarkCourse(id)
            } else {
                dao.bookmarkCourse(id)
            }
        }
    }

    override fun getCourseById(id: Int): Flow<CourseData> = flow {
        emitAll(
            dao.getCourseById(id).map {
                it.toCourseData()
            }
        )
    }

    override fun getBookmarkedCourses(): Flow<List<CourseData>> = flow {
        emitAll(
            dao.getBookmarkedCourses().map { entityList ->
                entityList.map {
                    it.toCourseData()
                }
            }
        )
    }

    override fun getStartedCourses(): Flow<List<CourseData>> = flow {
        emitAll(
            dao.getCourses().map { entityList ->
                entityList.map {
                    it.toCourseData()
                }
            }
        )
    }

    override suspend fun startCourse(courseId: Int) {
        dao.startCourse(courseId)
    }
}