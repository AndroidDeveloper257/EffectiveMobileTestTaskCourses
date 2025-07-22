package uz.alimov.effectivemobiletesttaskcourses.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.CheckAuthUseCase

class MainViewModel(
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch {
            _isLoggedIn.value = checkAuthUseCase.invoke()
        }
    }

}