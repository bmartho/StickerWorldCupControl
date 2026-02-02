package com.cup.stickerworldcupcontrol.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cup.stickerworldcupcontrol.components.CellComponent
import com.cup.stickerworldcupcontrol.database.dao.models.Cell

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    appViewModel: AppViewModel
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Fixed(10),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(400) { cell ->
            CellComponent(
                Cell(
                    id = 1,
                    text = "999",
                    isSelected = false
                )
            )
        }
    }
}