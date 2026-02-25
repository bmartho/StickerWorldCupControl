package com.cup.stickerworldcupcontrol.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cup.stickerworldcupcontrol.components.AdBanner
import com.cup.stickerworldcupcontrol.components.CellComponent

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    appViewModel: AppViewModel
) {
    val cells by appViewModel.listCells.collectAsState(initial = emptyList())
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val titles = listOf("Figurinhas", "Faltantes")

    Column(modifier = Modifier.padding(paddingValues)) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(8),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(
                count = cells.size,
                key = { index -> cells[index].id },
                contentType = { "sticker" }) { index ->
                val cell = cells[index]
                val isRepeatedTab by remember {
                    derivedStateOf { selectedTabIndex == 1 }
                }

                val onCellClick = remember(cell) { { appViewModel.onCellClick(cell) } }
                val onIncrease = remember(cell) { { appViewModel.onIncreaseRepeatedClick(cell) } }
                val onDecrease = remember(cell) { { appViewModel.onDecreaseRepeatedClick(cell) } }

                CellComponent(
                    cell = cells[index],
                    isRepeatedLayout = isRepeatedTab,
                    onClick = onCellClick,
                    onIncreaseRepeated = onIncrease,
                    onDecreaseRepeated = onDecrease
                )
            }
        }

        AdBanner()
    }
}