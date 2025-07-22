package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect get() = _effect.asSharedFlow()

    private var cyrillicRegex = Regex("[\\u0400-\\u04FF\\u0500-\\u052F]")

    fun onIntent(intent: LoginIntent) {
        viewModelScope.launch {
            when (intent) {
                is LoginIntent.EmailChanged -> {
                    val filteredEmail = intent.email.replace(cyrillicRegex, "")
                    _state.update {
                        it.copy(
                            email = filteredEmail,
                            emailEdited = true
                        )
                    }
                }

                LoginIntent.LoginClicked -> {
                    loginUseCase.invoke()
                    sendEffect(LoginEffect.NavigateToHome)
                }

                is LoginIntent.OkClicked -> {
                    sendEffect(LoginEffect.OpenOk(intent.url))
                }

                is LoginIntent.PasswordChanged -> {
                    _state.update {
                        it.copy(
                            password = intent.password,
                            passwordEdited = true
                        )
                    }
                }

                is LoginIntent.VkontakteClicked -> {
                    sendEffect(LoginEffect.OpenVk(intent.url))
                }
            }
        }
    }

    private suspend fun sendEffect(effect: LoginEffect) {
        _effect.emit(effect)
    }

}