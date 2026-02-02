package com.cup.stickerworldcupcontrol.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cup.stickerworldcupcontrol.database.dao.models.Cell

@Composable
fun CellComponent(
    cell: Cell
) {
    var backgroundColor = if (cell.isSelected) Color.Green else Color.White
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(backgroundColor),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = cell.text
        )
    }
}