package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login

import android.util.Patterns

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailEdited: Boolean = false,
    val passwordEdited: Boolean = false
) {
        val isEmailValid
        get() = if (!emailEdited) true
        else {
            if (email.isEmpty()) true
            else Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    val isPasswordValid
        get() = if (!passwordEdited) true else {
            if (password.isEmpty()) true
            else password.length >= 8
        }
//    val isEmailValid
//        get() = if (!emailEdited) true
//        else Patterns.EMAIL_ADDRESS.matcher(email).matches()
//
//    val isPasswordValid
//        get() = if (!passwordEdited) true
//        else password.length >= 8
    val isLoginButtonEnabled get() = isEmailValid && isPasswordValid && email.isNotEmpty() && password.isNotEmpty()
}