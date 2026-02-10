package com.cup.stickerworldcupcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.cup.stickerworldcupcontrol.database.AppDatabase
import com.cup.stickerworldcupcontrol.screens.AppViewModel
import com.cup.stickerworldcupcontrol.screens.MainScreen
import com.cup.stickerworldcupcontrol.ui.theme.StickerWorldCupControlTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        AppDatabase.getDatabase(applicationContext, lifecycleScope)
    }

    private val viewModel: AppViewModel by viewModels {
        AppViewModel.Factory(db.cellDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StickerWorldCupControlTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        paddingValues = innerPadding,
                        appViewModel = viewModel
                    )
                }
            }
        }
    }
}