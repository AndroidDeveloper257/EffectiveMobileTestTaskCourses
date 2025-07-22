package uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.R
import uz.alimov.effectivemobiletesttaskcourses.presentation.components.InputField
import uz.alimov.effectivemobiletesttaskcourses.presentation.ui.theme.EffectiveMobileTestTaskCoursesTheme
import uz.alimov.effectivemobiletesttaskcourses.presentation.utils.ConstValues.REGISTER_TAG

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onLogin: () -> Unit,
    openUrl: (String) -> Unit
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> {
                    onLogin()
                }

                is LoginEffect.OpenOk -> {
                    openUrl(effect.url)
                }

                is LoginEffect.OpenVk -> {
                    openUrl(effect.url)
                }
            }
        }
    }

    val okGradient = listOf(
        colorResource(R.color.yellow_1),
        colorResource(R.color.yellow_2)
    )
    val gradientBrush = Brush.verticalGradient(okGradient)
    val openRegister = buildAnnotatedString {
        append(stringResource(R.string.do_not_have_account))
        val start = length
        append(stringResource(R.string.register))
        val end = length
        addStringAnnotation(
            tag = REGISTER_TAG,
            annotation = LinkAnnotation.Url("register").url,
            start = start,
            end = end
        )
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary
            ),
            start = start,
            end = end
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 0.dp,
                bottom = 10.dp
            )
            .imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = stringResource(R.string.email),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(10.dp))
        InputField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.value.email,
            isError = !state.value.isEmailValid,
            errorText = stringResource(R.string.email_error),
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onValueChange = {
                viewModel.onIntent(LoginIntent.EmailChanged(it))
            },
            placeholder = {
                Text(text = stringResource(R.string.email_hint))
            }
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.password),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(10.dp))
        InputField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.value.password,
            isError = !state.value.isPasswordValid,
            errorText = stringResource(R.string.password_error),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onValueChange = {
                viewModel.onIntent(LoginIntent.PasswordChanged(it.filter { it.isLetterOrDigit() }))
            },
            placeholder = {
                Text(text = stringResource(R.string.password_hint))
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(15.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onIntent(LoginIntent.LoginClicked)
            },
            enabled = state.value.isLoginButtonEnabled,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.login),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = openRegister,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = TextAlign.Center
            ),
            onTextLayout = {}
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.forgot_password),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(20.dp))
        HorizontalDivider(thickness = 1.dp)
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(ButtonDefaults.MinHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue)
                ),
                onClick = {
                    viewModel.onIntent(LoginIntent.VkontakteClicked())
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_vkontakte),
                    contentDescription = null
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(brush = gradientBrush, shape = ButtonDefaults.shape)
                    .height(ButtonDefaults.MinHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    viewModel.onIntent(LoginIntent.OkClicked())
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_odnoklassniki),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    EffectiveMobileTestTaskCoursesTheme {
        LoginScreen(
            onLogin = {},
            openUrl = {}
        )
    }
}