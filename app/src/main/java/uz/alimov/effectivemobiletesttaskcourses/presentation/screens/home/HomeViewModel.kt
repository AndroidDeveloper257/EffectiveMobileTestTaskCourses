package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.alimov.effectivemobiletesttaskcourses.domain.Resource
import uz.alimov.effectivemobiletesttaskcourses.domain.model.CourseData
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.GetCoursesUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.ToggleBookmarkCourseUseCase

class HomeViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleBookmarkCourseUseCase: ToggleBookmarkCourseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect get() = _effect.asSharedFlow()

    init {
        onIntent(HomeIntent.LoadCourses)
    }

    fun onIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.BookmarkClicked -> {
                    toggleBookmarkCourseUseCase.invoke(intent.id)
                }

                is HomeIntent.CourseClicked -> {
                    sendEffect(HomeEffect.NavigateToDetails(intent.id))
                }

                HomeIntent.LoadCourses -> {
                    fetchCourses()
                }

                HomeIntent.SortClicked -> {
                    sortCourses()
                }
            }
        }
    }

    private suspend fun fetchCourses() {
        _state.update {
            it.copy(
                courseList = emptyList(),
                error = "",
                isLoading = true
            )
        }
        getCoursesUseCase.invoke()
            .onStart {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            .catch {
                _state.update {
                    it.copy(
                        isLoading = false,
                        courseList = emptyList()
                    )
                }
            }
            .collect {baseResult ->
                when (baseResult) {
                    is Resource.Error<*> -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = baseResult.rawResponse.toString(),
                                courseList = emptyList()
                            )
                        }
                    }
                    is Resource.Success<*> -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                courseList = baseResult.data as List<CourseData>,
                                error = ""
                            )
                        }
                    }
                }
            }
    }

    private fun sortCourses() {
        val courses = state.value.courseList.sortedByDescending { it.publishDate }
        _state.update {
            it.copy(
                courseList = courses
            )
        }
    }

    private suspend fun sendEffect(effect: HomeEffect) {
        _effect.emit(effect)
    }

}