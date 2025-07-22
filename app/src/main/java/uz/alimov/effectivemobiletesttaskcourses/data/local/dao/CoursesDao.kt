package uz.alimov.effectivemobiletesttaskcourses.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.alimov.effectivemobiletesttaskcourses.data.local.entity.CourseEntity

@Dao
interface CoursesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("SELECT * FROM courses_table")
    fun getCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses_table WHERE id = :id")
    fun getCourseById(id: Int): Flow<CourseEntity>

    @Query("UPDATE courses_table SET has_started = 1 WHERE id = :courseId")
    suspend fun startCourse(courseId: Int)

    @Query("SELECT * FROM courses_table WHERE has_started = 1")
    fun getStartedCourses(): Flow<List<CourseEntity>>

    @Query("UPDATE courses_table SET has_like = 1 WHERE id = :courseId")
    suspend fun bookmarkCourse(courseId: Int)

    @Query("UPDATE courses_table SET has_like = 0 WHERE id = :courseId")
    suspend fun unBookmarkCourse(courseId: Int)

    @Query("SELECT has_like FROM courses_table WHERE id = :courseId")
    fun isCourseBookmarked(courseId: Int): Boolean

    @Query("SELECT * FROM courses_table WHERE has_like = 1")
    fun getBookmarkedCourses(): Flow<List<CourseEntity>>

    @Query("SELECT COUNT(*) == 0 FROM courses_table")
    fun isEmpty(): Boolean

    @Query("DELETE FROM courses_table")
    suspend fun deleteAll()
}