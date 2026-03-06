package com.cup.stickerworldcupcontrol.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
                    "${"%.1f".format(percentage)}%"
                ),
                fontSize = 13.sp
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
        ) {
            if (cells.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.loading_stickers),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        lineHeight = 4.sp,
                        fontWeight = FontWeight.W900,
                        maxLines = 1,
                    )
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(5),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = spacedBy(4.dp),
                    horizontalArrangement = spacedBy(4.dp)
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

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Secondary,
            contentColor = Color.White,
            divider = {},
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .fillMaxSize()
                        .padding(4.dp)
                        .background(
                            color = TabIndicator.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = TabIndicator,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                val selected = selectedTabIndex == index
                Tab(
                    selected = selected,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title.uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected) TabIndicator else Color.White
                        )
                    }
                )
            }
        }

        AdBanner()
    }
}