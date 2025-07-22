package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.CourseByIdUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.StartCourseUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.ToggleBookmarkCourseUseCase

class DetailsViewModel(
    private val courseByIdUseCase: CourseByIdUseCase,
    private val startCourseUseCase: StartCourseUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkCourseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state get() = _state.asStateFlow()

    fun onIntent(intent: DetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is DetailsIntent.LoadCourse -> {
                    fetchCourse(intent.id)
                }

                DetailsIntent.StartCourseClicked -> {
                    startCourse()
                }

                DetailsIntent.ToggleBookmarkClicked -> {
                    toggleBookmark()
                }
            }
        }
    }

    private suspend fun fetchCourse(id: Int) {
        courseByIdUseCase.invoke(id)
            .onStart {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            .collect { courseData ->
                _state.update {
                    it.copy(
                        course = courseData,
                        isLoading = false
                    )
                }
            }
    }

    private suspend fun startCourse() {
        startCourseUseCase.invoke(_state.value.course.id ?: 0)
    }

    private suspend fun toggleBookmark() {
        toggleBookmarkUseCase.invoke(_state.value.course.id ?: 0)
    }

}