package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cup.stickerworldcupcontrol.R
import com.cup.stickerworldcupcontrol.ui.theme.ButtonColor
import com.cup.stickerworldcupcontrol.ui.theme.Secondary

@Composable
fun ShareDialog(
    onConfirm: (
        shareMissing: Boolean,
        shareRepeated: Boolean,
        shareNumberRepeated: Boolean
    ) -> Unit,
    onDismiss: () -> Unit
) {
    var shareMissing by remember { mutableStateOf(true) }
    var shareRepeated by remember { mutableStateOf(true) }
    var shareNumberRepeated by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.share_dialog_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { shareMissing = !shareMissing }
                ) {
                    Checkbox(
                        checked = shareMissing,
                        onCheckedChange = { shareMissing = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Secondary
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.share_option_missing),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        shareRepeated = !shareRepeated
                        if (!shareRepeated) {
                            shareNumberRepeated = false
                        }
                    }
                ) {
                    Checkbox(
                        checked = shareRepeated,
                        onCheckedChange = {
                            shareRepeated = it
                            if (!shareRepeated) {
                                shareNumberRepeated = false
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Secondary
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.share_option_repeated),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        shareNumberRepeated = !shareNumberRepeated
                        if (shareNumberRepeated) {
                            shareRepeated = true
                        }
                    }
                ) {
                    Checkbox(
                        checked = shareNumberRepeated,
                        onCheckedChange = {
                            shareNumberRepeated = it
                            if (shareNumberRepeated) {
                                shareRepeated = true
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Secondary
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.share_option_repeated_count),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(shareMissing, shareRepeated, shareNumberRepeated)
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ButtonColor
                ),
                enabled = shareMissing || shareRepeated
            ) {
                Text(
                    text = stringResource(id = R.string.btn_share),
                    color = if (shareMissing || shareRepeated) {
                        Color.Unspecified
                    } else {
                        Color.Gray
                    }
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