package com.cup.stickerworldcupcontrol.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.cup.stickerworldcupcontrol.extensions.toStringId
import com.cup.stickerworldcupcontrol.ui.theme.ButtonColor
import java.text.Collator
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoToDialog(
    sectionSimbolList: List<String>,
    onDismiss: () -> Unit,
    onSectionClick: (String) -> Unit
) {
    var isAlphabeticalOrder by remember { mutableStateOf(true) }

    val sectionsWithNames = sectionSimbolList.map {
        it to stringResource(id = it.toStringId())
    }

    val finalSectionList = remember(isAlphabeticalOrder, sectionsWithNames) {
        if (isAlphabeticalOrder) {
            val collator = Collator.getInstance(Locale.getDefault()).apply {
                strength = Collator.PRIMARY
            }
            sectionsWithNames.sortedWith(compareBy(collator) { it.second })
        } else {
            sectionsWithNames
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.filter_dialog_title),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )

                FilterChip(
                    selected = isAlphabeticalOrder,
                    onClick = { isAlphabeticalOrder = !isAlphabeticalOrder },
                    label = { Text("A-Z") },
                    leadingIcon = if (isAlphabeticalOrder) {
                        { Icon(Icons.Default.ArrowDropDown, null, modifier = Modifier.size(18.dp)) }
                    } else null,
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isAlphabeticalOrder) ButtonColor else Color.Gray,
                        selectedBorderColor = ButtonColor,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp,
                        enabled = true,
                        selected = false
                    ),
                    colors = FilterChipDefaults.filterChipColors(
                        labelColor = Color.Black,
                        selectedLabelColor = Color.White,
                        selectedContainerColor = ButtonColor
                    )
                )
            }
        },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(finalSectionList) { index, pair ->
                    val sectionSimbol = pair.first
                    val sectionName = pair.second

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSectionClick(sectionSimbol) }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = sectionName,
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = ButtonColor
                            )
                        }

                        if (index < finalSectionList.size - 1) {
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ButtonColor
                )
            ) {
                Text(text = stringResource(id = R.string.btn_cancel))
            }
        }
    )
}