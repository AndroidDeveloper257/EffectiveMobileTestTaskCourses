package uz.alimov.effectivemobiletesttaskcourses.domain.usecase

import uz.alimov.effectivemobiletesttaskcourses.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke() = repository.login()
}