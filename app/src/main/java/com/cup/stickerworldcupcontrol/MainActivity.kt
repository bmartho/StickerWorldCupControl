package com.cup.stickerworldcupcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.cup.stickerworldcupcontrol.database.AppDatabase
import com.cup.stickerworldcupcontrol.screens.AppViewModel
import com.cup.stickerworldcupcontrol.screens.MainScreen
import com.cup.stickerworldcupcontrol.ui.theme.StickerWorldCupControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        val viewModel = AppViewModel(db.cellDao())
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