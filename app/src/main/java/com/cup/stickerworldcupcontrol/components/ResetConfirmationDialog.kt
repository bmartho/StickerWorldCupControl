package com.cup.stickerworldcupcontrol.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cup.stickerworldcupcontrol.R
import com.cup.stickerworldcupcontrol.ui.theme.ButtonColor

@Composable
fun ResetConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.reset_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.reset_dialog_text)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(id = R.string.btn_clear_all),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ButtonColor
                )
            ) {
                Text(text = stringResource(id = R.string.btn_cancel))
            }
        }
    )
}