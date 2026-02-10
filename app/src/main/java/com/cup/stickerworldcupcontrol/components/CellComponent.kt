package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cup.stickerworldcupcontrol.database.dao.models.Cell

@Composable
fun CellComponent(
    cell: Cell,
    onClick: () -> Unit
) {
    val backgroundColor = if (cell.isSelected) Color.Green else Color.White
    Box(
        modifier = Modifier
            .height(60.dp)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = cell.text,
            color = Color.Black,
            maxLines = 1,
            fontSize = 13.sp
        )
    }
}