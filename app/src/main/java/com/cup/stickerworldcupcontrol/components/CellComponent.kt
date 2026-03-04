package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cup.stickerworldcupcontrol.database.dao.models.Cell
import com.cup.stickerworldcupcontrol.ui.theme.RepeatedSticker
import com.cup.stickerworldcupcontrol.ui.theme.SelectedSticker

@Composable
fun CellComponent(
    cell: Cell,
    isRepeatedLayout: Boolean,
    onClick: () -> Unit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
) {
    val backgroundColor = remember(cell.isSelected, cell.numberRepeated, isRepeatedLayout) {
        if (isRepeatedLayout) {
            if (cell.numberRepeated > 0) RepeatedSticker else Color.White
        } else {
            if (cell.isSelected) SelectedSticker else Color.White
        }
    }

    val baseModifier = Modifier
        .height(110.dp)
        .drawBehind {
            drawRect(backgroundColor)
        }
        .border(1.dp, Color.Black)

    if (isRepeatedLayout) {
        Column(
            modifier = baseModifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 2.dp),
                text = cell.text,
                color = Color.Black,
                maxLines = 1,
                fontSize = 16.sp,
                lineHeight = 4.sp,
                fontWeight = FontWeight.W900,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
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
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            ButtonCellComponent(
                modifier = Modifier,
                text = "-",
                onClick = onDecreaseRepeated
            )
        }
    } else {
        Box(
            modifier = baseModifier
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
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
            .size(26.dp)
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
            fontSize = 12.sp,
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