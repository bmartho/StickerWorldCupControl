package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cup.stickerworldcupcontrol.R
import com.cup.stickerworldcupcontrol.ui.theme.ButtonColor

@Composable
fun AboutDialog(
    appVersion: String,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text(text = stringResource(id = R.string.about_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = stringResource(id = R.string.about_creator))
                Text(text = stringResource(id = R.string.about_version, appVersion))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ButtonColor
                )
            ) {
                Text(text = stringResource(id = R.string.btn_ok))
            }
        },
    )
}