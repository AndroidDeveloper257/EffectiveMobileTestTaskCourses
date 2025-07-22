package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home

sealed interface HomeEffect {
    data class NavigateToDetails(val id: Int) : HomeEffect
}