package uz.alimov.effectivemobiletesttaskcourses.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean,
    errorText: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(
        modifier = modifier
    ) {
        TextField(
            modifier = modifier
                .height(TextFieldDefaults.MinHeight)
                .clip(
                    RoundedCornerShape(40.dp)
                )
                .background(MaterialTheme.colorScheme.surface),
            value = value,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            ),
            onValueChange = {
                onValueChange(it)
            },
            placeholder = placeholder,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            visualTransformation = visualTransformation
        )
        AnimatedVisibility(isError) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}