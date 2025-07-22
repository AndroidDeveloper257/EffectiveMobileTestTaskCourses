package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.BookmarkedCoursesUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.ToggleBookmarkCourseUseCase

class FavoritesViewModel(
    private val bookmarkedCoursesUseCase: BookmarkedCoursesUseCase,
    private val toggleBookmarkCourseUseCase: ToggleBookmarkCourseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FavoritesEffect>()
    val effect get() = _effect.asSharedFlow()

    init {
        onIntent(FavoritesIntent.FetchCourses)
    }

    fun onIntent(intent: FavoritesIntent) {
        viewModelScope.launch {
            when (intent) {
                is FavoritesIntent.BookmarkClicked -> {
                    toggleBookmarkCourseUseCase.invoke(intent.id)
                }

                is FavoritesIntent.CourseClicked -> {
                    sendEffect(FavoritesEffect.NavigateToDetails(intent.id))
                }

                FavoritesIntent.FetchCourses -> {
                    fetchCourses()
                }

                FavoritesIntent.NavigateHomeClicked -> {
                    sendEffect(FavoritesEffect.NavigateToHome)
                }
            }
        }
    }

    private suspend fun fetchCourses() {
        bookmarkedCoursesUseCase.invoke()
            .collect {
                _state.value = _state.value.copy(
                    savedCourses = it
                )
            }
    }

    private suspend fun sendEffect(effect: FavoritesEffect) {
        _effect.emit(effect)
    }

}