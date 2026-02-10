package com.cup.stickerworldcupcontrol.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cup.stickerworldcupcontrol.components.CellComponent
import com.cup.stickerworldcupcontrol.database.dao.models.Cell

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    appViewModel: AppViewModel
) {
    val cells by appViewModel.listCells.collectAsState(initial = emptyList())
    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Fixed(10),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(cells.size) { index ->
            with(cells[index]) {
                CellComponent(
                    Cell(
                        id = id,
                        text = text,
                        isSelected = isSelected
                    )
                )
            }
        }
    }
}