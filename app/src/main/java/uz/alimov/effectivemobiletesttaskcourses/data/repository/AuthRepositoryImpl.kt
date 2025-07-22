package uz.alimov.effectivemobiletesttaskcourses.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import uz.alimov.effectivemobiletesttaskcourses.data.local.dao.CoursesDao
import uz.alimov.effectivemobiletesttaskcourses.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dao: CoursesDao,
    private val sharedPref: SharedPreferences
): AuthRepository {
    override suspend fun login() {
        sharedPref.edit {
            putBoolean("isLoggedIn", true)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    override suspend fun logout() {
        sharedPref.edit {
            putBoolean("isLoggedIn", false)
        }
        dao.deleteAll()
    }
}