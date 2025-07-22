package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.LogoutUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.StartedCoursesUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.ToggleBookmarkCourseUseCase

class ProfileViewModel(
    private val startedCoursesUseCase: StartedCoursesUseCase,
    private val toggleBookmarkCourseUseCase: ToggleBookmarkCourseUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effect get() = _effect.asSharedFlow()

    init {
        onIntent(ProfileIntent.FetchCourses)
    }

    fun onIntent(intent: ProfileIntent) {
        viewModelScope.launch {
            when (intent) {
                is ProfileIntent.BookmarkClicked -> {
                    toggleBookmarkCourseUseCase.invoke(intent.id)
                }

                is ProfileIntent.CourseClicked -> {
                    sendEffect(ProfileEffect.NavigateToDetails(intent.id))
                }

                ProfileIntent.FetchCourses -> {
                    fetchStartedCourses()
                }

                ProfileIntent.LogoutClicked -> {
                    logoutUseCase.invoke()
                    sendEffect(ProfileEffect.Logout)
                }

                ProfileIntent.NavigateHomeClicked -> {
                    sendEffect(ProfileEffect.NavigateHome)
                }
            }
        }
    }

    private suspend fun fetchStartedCourses() {
        startedCoursesUseCase.invoke()
            .collect { startedCourses ->
                _state.update {
                    it.copy(
                        courses = startedCourses
                    )
                }
            }
    }

    private suspend fun sendEffect(effect: ProfileEffect) {
        _effect.emit(effect)
    }

}