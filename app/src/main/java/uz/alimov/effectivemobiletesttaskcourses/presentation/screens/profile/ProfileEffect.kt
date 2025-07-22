package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile

sealed interface ProfileEffect {
    object NavigateHome : ProfileEffect
    data class NavigateToDetails(val id: Int) : ProfileEffect
    object Logout : ProfileEffect
}