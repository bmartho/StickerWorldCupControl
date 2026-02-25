package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cup.stickerworldcupcontrol.database.dao.models.Cell

@Composable
fun CellComponent(
    cell: Cell,
    isRepeatedLayout: Boolean,
    onClick: () -> Unit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
) {
    if (isRepeatedLayout) {
        val backgroundColor = if (cell.numberRepeated > 0) Color.Gray else Color.White
        Column(
            modifier = Modifier
                .height(90.dp)
                .background(backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cell.text,
                color = Color.Black,
                maxLines = 1,
                fontSize = 10.sp,
                lineHeight = 4.sp,
                fontWeight = FontWeight.Bold,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false // Ajuda a manter o texto no centro
                    )
                )
            )
            HorizontalDivider()

            ButtonCellComponent(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = "+",
                onClick = onIncreaseRepeated
            )

            Text(
                text = cell.numberRepeated.toString(),
                color = Color.Black,
                maxLines = 1,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )

            ButtonCellComponent(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = "-",
                onClick = onDecreaseRepeated
            )
        }
    } else {
        val backgroundColor = if (cell.isSelected) Color.Green else Color.White
        Box(
            modifier = Modifier
                .height(90.dp)
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
}

@Composable
fun ButtonCellComponent(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color(0xFFE0E0E0))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            maxLines = 1,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            style = LocalTextStyle.current.copy(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
                lineHeight = 13.sp
            )
        )
    }
}