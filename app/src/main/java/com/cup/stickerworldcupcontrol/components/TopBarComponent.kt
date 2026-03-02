package com.cup.stickerworldcupcontrol.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.cup.stickerworldcupcontrol.R
import com.cup.stickerworldcupcontrol.ui.theme.Primary
import com.cup.stickerworldcupcontrol.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    showResetDialog: () -> Unit,
    shareDialog: () -> Unit,
    aboutDialog: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title =
            {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            },
        actions =
            {
                IconButton(onClick = {
                    shareDialog()
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(id = R.string.content_desc_share)
                    )
                }

                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(id = R.string.content_desc_options)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.menu_clear_album)) },
                        onClick = {
                            showResetDialog()
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.menu_about)) },
                        onClick = {
                            aboutDialog()
                            showMenu = false
                        }
                    )
                }
            },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Secondary,
            titleContentColor = Primary,
        )
    )
}