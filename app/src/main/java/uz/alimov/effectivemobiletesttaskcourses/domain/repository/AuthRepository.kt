package uz.alimov.effectivemobiletesttaskcourses.domain.repository

interface AuthRepository {

    suspend fun login()

    suspend fun isLoggedIn(): Boolean

    suspend fun logout()
}