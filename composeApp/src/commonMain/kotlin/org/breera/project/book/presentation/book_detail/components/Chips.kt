package org.breera.project.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.breera.project.core.presentation.LightBlue
import org.breera.project.core.presentation.SandYellow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Chips(
    title: String,
    chipTexts: List<String>,
    isShowTrailingIcon: Boolean = false
) {
    Column(
        Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            chipTexts.forEach {
                AssistChip(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(45),
                    colors = AssistChipDefaults.assistChipColors().copy(
                        containerColor = LightBlue,
                        labelColor = Color.Black
                    ),
                    border = BorderStroke(0.0.dp, Color.LightGray),
                    onClick = { },
                    label = {
                        Text(modifier = Modifier.padding(vertical = 10.dp), text = it.toString())
                    },
                    trailingIcon = {
                        AnimatedVisibility(isShowTrailingIcon) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "rating",
                                Modifier.size(AssistChipDefaults.IconSize),
                                tint = SandYellow
                            )
                        }
                    }
                )
            }
        }
    }
}
