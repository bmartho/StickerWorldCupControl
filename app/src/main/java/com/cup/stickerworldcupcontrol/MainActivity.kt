package com.cup.stickerworldcupcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.cup.stickerworldcupcontrol.components.ResetConfirmationDialog
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
            if (showResetDialog) {
                ResetConfirmationDialog(
                    onConfirm = {
                        viewModel.cleanAlbum()
                        showResetDialog = false
                    },
                    onDismiss = { showResetDialog = false }
                )
            }

            StickerWorldCupControlTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBarComponent {
                            showResetDialog = true
                        }
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