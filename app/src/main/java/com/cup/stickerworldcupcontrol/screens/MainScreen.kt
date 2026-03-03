package com.cup.stickerworldcupcontrol.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cup.stickerworldcupcontrol.R
import com.cup.stickerworldcupcontrol.components.AdBanner
import com.cup.stickerworldcupcontrol.components.CellComponent
import com.cup.stickerworldcupcontrol.ui.theme.Secondary
import com.cup.stickerworldcupcontrol.ui.theme.TabIndicator

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    appViewModel: AppViewModel
) {
    val cells by appViewModel.listCells.collectAsState(initial = emptyList())
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val collectedNumber = cells.count { it.isSelected }
    val percentage = if (cells.isNotEmpty()) {
        (collectedNumber.toFloat() / cells.size.toFloat()) * 100
    } else {
        0f
    }

    val titles = listOf(
        stringResource(id = R.string.tab_collected),
        stringResource(id = R.string.tab_repeated)
    )

    Column(modifier = Modifier.padding(paddingValues)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Secondary)
                .padding(bottom = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(
                    R.string.total_stickers,
                    collectedNumber,
                    cells.size,
                    "${"%.0f".format(percentage)}%"
                ),
                fontSize = 13.sp
            )
        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 4.dp,
                    color = TabIndicator
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (cells.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.loading_stickers),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
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
                        val onIncrease =
                            remember(cell) { { appViewModel.onIncreaseRepeatedClick(cell) } }
                        val onDecrease =
                            remember(cell) { { appViewModel.onDecreaseRepeatedClick(cell) } }

                        CellComponent(
                            cell = cells[index],
                            isRepeatedLayout = isRepeatedTab,
                            onClick = onCellClick,
                            onIncreaseRepeated = onIncrease,
                            onDecreaseRepeated = onDecrease
                        )
                    }
                }
            }
        }

        AdBanner()
    }
}