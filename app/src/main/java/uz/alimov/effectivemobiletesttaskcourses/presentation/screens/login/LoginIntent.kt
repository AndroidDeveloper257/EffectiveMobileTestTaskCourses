package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login

import uz.alimov.effectivemobiletesttaskcourses.presentation.utils.ConstValues.OK_BASE_URL
import uz.alimov.effectivemobiletesttaskcourses.presentation.utils.ConstValues.VK_BASE_URL

sealed interface LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data class VkontakteClicked(val url: String = VK_BASE_URL) : LoginIntent
    data class OkClicked(val url: String = OK_BASE_URL) : LoginIntent
    object LoginClicked : LoginIntent
}