package uz.alimov.effectivemobiletesttaskcourses.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.alimov.effectivemobiletesttaskcourses.data.local.dao.CoursesDao
import uz.alimov.effectivemobiletesttaskcourses.data.local.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 1)
abstract class CoursesDatabase : RoomDatabase() {

    abstract fun coursesDao(): CoursesDao

}