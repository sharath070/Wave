package com.sharath070.wave.presentation.feature.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.brandColor
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@Composable
fun ActionChips(
    category: String,
    enabled: Boolean,
    onChipSelected: () -> Unit
) {
    FilterChip(
        selected = enabled,
        onClick = onChipSelected,
        label = {
            Text(
                text = category,
                color = text,
                fontFamily = regular,
                fontSize = 16.sp
            )
        },
        shape = RoundedCornerShape(100.dp),
        colors = SelectableChipColors(
            containerColor = bg,
            selectedContainerColor = brandColor.copy(alpha = 0.6f),
            disabledContainerColor = bg,
            disabledSelectedContainerColor = brandColor.copy(alpha = 0.6f),
            labelColor = text,
            disabledLabelColor = text,
            selectedLabelColor = text,
            leadingIconColor = Color.Transparent,
            selectedLeadingIconColor = Color.Transparent,
            disabledLeadingIconColor = Color.Transparent,
            trailingIconColor = Color.Transparent,
            selectedTrailingIconColor = Color.Transparent,
            disabledTrailingIconColor = Color.Transparent
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = enabled,
            selected = enabled,
            selectedBorderColor = brandColor,
            disabledBorderColor = textDisabled,
            selectedBorderWidth = 1.dp,
            borderWidth = 1.dp
        ),
        modifier = Modifier.padding(start = 8.dp)
    )
}