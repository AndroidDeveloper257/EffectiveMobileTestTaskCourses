package uz.alimov.effectivemobiletesttaskcourses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyListMessage(
    modifier: Modifier = Modifier,
    message: Int,
    tryAgainButton: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(message),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(10.dp))
        tryAgainButton()
    }
}