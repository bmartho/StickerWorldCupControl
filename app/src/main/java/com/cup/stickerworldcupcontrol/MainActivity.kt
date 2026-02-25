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
import androidx.lifecycle.lifecycleScope
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
    private val db by lazy {
        AppDatabase.getDatabase(applicationContext, lifecycleScope)
    }

    private val viewModel: AppViewModel by viewModels {
        AppViewModel.Factory(db.cellDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        enableEdgeToEdge()
        setContent {
            var showResetDialog by remember { mutableStateOf(false) }
            var shareDialog by remember { mutableStateOf(false) }
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
                ShareDialog(
                    onConfirm = { shareMissing, shareRepeated, shareNumberRepeated ->
                        var shareText = ""

                        if (shareMissing) {
                            shareText += "Figurinhas faltantes:\n\n"
                            shareText += cells
                                .filter { !it.isSelected }
                                .joinToString(", ") { it.text }

                            if (shareRepeated) {
                                shareText += "\n\n"
                            }
                        }

                        if (shareRepeated) {
                            shareText += "Figurinhas repetidas:\n\n"
                            shareText += cells
                                .filter { it.numberRepeated > 0 }
                                .joinToString(", ") {
                                    if (!shareNumberRepeated) {
                                        it.text
                                    } else {
                                        if (it.numberRepeated <= 1) {
                                            it.text
                                        } else {
                                            "${it.text} (${it.numberRepeated})"
                                        }
                                    }
                                }
                        }

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        startActivity(Intent.createChooser(intent, "Compartilhar via"))
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
                            }
                        )
                    }
                ) { innerPadding ->
                    MainScreen(
                        paddingValues = innerPadding,
                        appViewModel = viewModel
                    )
                }
            }
        }
    }
}