package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.cup.stickerworldcupcontrol.ui.theme.RepeatedStickerStrong
import com.cup.stickerworldcupcontrol.ui.theme.SelectedSticker
import com.cup.stickerworldcupcontrol.ui.theme.SelectedStickerStrong
import com.cup.stickerworldcupcontrol.ui.theme.WhiteStrong

@Composable
fun CellComponent(
    cell: Cell,
    isRepeatedLayout: Boolean,
    onClick: () -> Unit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
) {
    val backgroundColor = remember(cell.isSelected, cell.numberRepeated, isRepeatedLayout) {
        val isStickerActive = if (isRepeatedLayout) cell.numberRepeated > 0 else cell.isSelected

        when {
            isStickerActive && isRepeatedLayout -> if (cell.isStrongColor) RepeatedStickerStrong else RepeatedSticker
            isStickerActive && !isRepeatedLayout -> if (cell.isStrongColor) SelectedStickerStrong else SelectedSticker
            cell.isStrongColor -> WhiteStrong
            else -> Color.White
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
                text = "${cell.label} ${cell.text}",
                color = Color.Black,
                maxLines = 1,
                fontSize = 14.sp,
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
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            ButtonCellComponent(
                modifier = Modifier,
                text = "-",
                onClick = onDecreaseRepeated
            )
        }
    } else {
        Column(
            modifier = baseModifier
                .clickable(onClick = onClick),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cell.label,
                color = Color.Black,
                maxLines = 1,
                fontSize = 16.sp
            )

            Text(
                text = cell.text,
                color = Color.Black,
                maxLines = 1,
                fontSize = 16.sp
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
            .background(Color(0xFFBBBABA))
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