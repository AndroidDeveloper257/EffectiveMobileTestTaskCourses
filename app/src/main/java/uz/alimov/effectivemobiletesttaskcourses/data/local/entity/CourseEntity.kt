package uz.alimov.effectivemobiletesttaskcourses.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "courses_table"
)
data class CourseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val rate: String,
    val price: String,
    @ColumnInfo(name = "has_like")
    val hasLike: Boolean,
    @ColumnInfo(name = "publish_date")
    val publishDate: String,
    val text: String,
    val title: String,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "cover_resource")
    val coverResource: Int,
    @ColumnInfo(name = "has_started")
    val hasStarted: Boolean
)