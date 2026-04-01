package com.cup.stickerworldcupcontrol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.cup.stickerworldcupcontrol.components.AboutDialog
import com.cup.stickerworldcupcontrol.components.ResetConfirmationDialog
import com.cup.stickerworldcupcontrol.components.ShareDialog
import com.cup.stickerworldcupcontrol.components.TopBarComponent
import com.cup.stickerworldcupcontrol.database.AppDatabase
import com.cup.stickerworldcupcontrol.screens.AppViewModel
import com.cup.stickerworldcupcontrol.screens.MainScreen
import com.cup.stickerworldcupcontrol.ui.theme.StickerWorldCupControlTheme
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val context = this
    private val db by lazy {
        AppDatabase.getDatabase(applicationContext, lifecycleScope)
    }

    private val viewModel: AppViewModel by viewModels {
        AppViewModel.Factory(db.cellDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        enableEdgeToEdge()
        setContent {
            var showResetDialog by remember { mutableStateOf(false) }
            var shareDialog by remember { mutableStateOf(false) }
            var aboutDialog by remember { mutableStateOf(false) }
            var goToDialog by remember { mutableStateOf(false) }
            if (aboutDialog) {
                AboutDialog(
                    appVersion = context.packageManager.getPackageInfo(
                        context.packageName,
                        0
                    ).versionName ?: "1.0",
                    onConfirm = {
                        aboutDialog = false
                    }
                )
            }

            if (showResetDialog) {
                ResetConfirmationDialog(
                    onConfirm = {
                        viewModel.cleanAlbum()
                        showResetDialog = false
                    },
                    onDismiss = { showResetDialog = false }
                )
            }

            if (shareDialog) {
                val cells by viewModel.listCells.collectAsState(initial = emptyList())
                val headerMissing = stringResource(id = R.string.share_header_missing)
                val headerRepeated = stringResource(id = R.string.share_header_repeated)
                val chooserTitle = stringResource(id = R.string.share_chooser_title)

                ShareDialog(
                    onConfirm = { shareMissing, shareRepeated, shareNumberRepeated ->
                        val shareText = StringBuilder()

                        if (shareMissing) {
                            shareText.append(headerMissing)
                            val groupedMissing =
                                cells.filter { !it.isSelected }.groupBy { it.label }

                            groupedMissing.forEach { (label, items) ->
                                val numbers = items.joinToString(", ") { it.text }
                                shareText.append("$label: $numbers\n\n")
                            }

                            if (shareRepeated) {
                                shareText.append("\n")
                            }
                        }

                        if (shareRepeated) {
                            shareText.append(headerRepeated)

                            val groupedRepeated =
                                cells.filter { it.numberRepeated > 0 }.groupBy { it.label }

                            groupedRepeated.forEach { (label, items) ->
                                val stickers = items.joinToString(", ") { cell ->
                                    val stickerBase = cell.text
                                    if (shareNumberRepeated && cell.numberRepeated > 1) {
                                        "$stickerBase (${cell.numberRepeated})"
                                    } else {
                                        stickerBase
                                    }
                                }
                                shareText.append("$label: $stickers\n\n")
                            }
                        }

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText.toString())
                        }
                        context.startActivity(Intent.createChooser(intent, chooserTitle))
                        shareDialog = false
                    },
                    onDismiss = { shareDialog = false }
                )
            }

            StickerWorldCupControlTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBarComponent(
                            showResetDialog = {
                                showResetDialog = true
                            },
                            shareDialog = {
                                shareDialog = true
                            },
                            aboutDialog = {
                                aboutDialog = true
                            },
                            onGoToDialog = {
                                goToDialog = true
                            }
                        )
                    }
                ) { innerPadding ->
                    MainScreen(
                        paddingValues = innerPadding,
                        appViewModel = viewModel,
                        goToDialog = goToDialog
                    ) {
                        goToDialog = false
                    }
                }
            }
        }
    }
}