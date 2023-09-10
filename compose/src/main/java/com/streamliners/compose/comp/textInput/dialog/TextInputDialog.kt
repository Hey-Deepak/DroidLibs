package com.streamliners.compose.comp.textInput.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.TextInputState
import com.streamliners.compose.comp.textInput.dialog.TextInputDialogState.Hidden
import com.streamliners.compose.comp.textInput.value

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun TextInputDialog(
    state: MutableState<TextInputDialogState>
) {
    val data = state.value as? TextInputDialogState.Visible ?: return

    val dismiss = { state.value = Hidden }

    AlertDialog(
        modifier = Modifier
            .padding(28.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = dismiss,
        title = {
            Text(text = data.title)
        },
        text = {
            TextInputLayout(
                modifier = Modifier.fillMaxWidth(),
                state = data.input,
                maxLength = data.maxLength
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val isValid = TextInputState.validateAll(
                        data.input
                    )

                    if (!isValid) return@TextButton

                    val input = data.input.value()
                    data.submit(input)
                }
            ) {
                Text(
                    data.submitButtonLabel,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismiss
            ) {
                Text(
                    "Dismiss",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}