package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login

sealed interface LoginEffect {
    object NavigateToHome : LoginEffect
    data class OpenVk(val url: String) : LoginEffect
    data class OpenOk(val url: String) : LoginEffect
}