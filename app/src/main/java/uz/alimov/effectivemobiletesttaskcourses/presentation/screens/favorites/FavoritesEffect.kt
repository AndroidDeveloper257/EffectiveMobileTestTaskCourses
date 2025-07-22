package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites

sealed interface FavoritesEffect {
    data class NavigateToDetails(val id: Int) : FavoritesEffect
    object NavigateToHome : FavoritesEffect
}