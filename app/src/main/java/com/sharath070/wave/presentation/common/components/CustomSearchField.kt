package com.sharath070.wave.presentation.common.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.sharath070.wave.presentation.ui.theme.brandColorLight
import com.sharath070.wave.presentation.ui.theme.layer
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchField(
    placeholder: String = "",
    onValueChange: (String) -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var textState by remember { mutableStateOf("") }

    BasicTextField(
        value = textState,
        onValueChange = {
            textState = it
            onValueChange(it)
        },
        singleLine = true,
        cursorBrush = Brush.verticalGradient(listOf(brandColorLight, brandColorLight)),
        maxLines = 1,
        textStyle = TextStyle(
            color = text,
            fontFamily = medium,
            fontSize = 18.sp
        ),
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 21.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(7.dp))
            .shadow(10.dp)
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = textState,
            innerTextField = {
                innerTextField()
            },
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = placeholder,
                    color = textDisabled,
                    fontFamily = medium,
                    fontSize = 18.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "search icon",
                    tint = text,
                    modifier = Modifier
                        .height(22.dp)
                        .aspectRatio(1f)
                )
            },
            colors = TextFieldColors(
                focusedContainerColor = layer.copy(0.5f),
                unfocusedContainerColor = layer.copy(0.5f),
                disabledContainerColor = layer.copy(0.5f),
                errorContainerColor = layer.copy(0.5f),
                unfocusedTextColor = text,
                disabledTextColor = text,
                focusedTextColor = text,
                textSelectionColors = TextSelectionColors(
                    backgroundColor = brandColorLight.copy(0.5f),
                    handleColor = brandColorLight
                ),
                disabledSupportingTextColor = text,
                errorSupportingTextColor = text,
                errorTextColor = text,
                focusedSupportingTextColor = text,
                unfocusedSupportingTextColor = text,
                cursorColor = brandColorLight,
                errorCursorColor = brandColorLight,
                unfocusedPlaceholderColor = textDisabled,
                focusedPlaceholderColor = textDisabled,
                disabledPlaceholderColor = textDisabled,
                errorPlaceholderColor = textDisabled,
                focusedLabelColor = textDisabled,
                disabledLabelColor = textDisabled,
                errorLabelColor = textDisabled,
                unfocusedLabelColor = textDisabled,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledLeadingIconColor = textDisabled,
                errorLeadingIconColor = textDisabled,
                focusedLeadingIconColor = text,
                unfocusedLeadingIconColor = text,
                disabledTrailingIconColor = textDisabled,
                errorTrailingIconColor = textDisabled,
                focusedTrailingIconColor = text,
                unfocusedTrailingIconColor = text,
                disabledPrefixColor = text,
                errorPrefixColor = text,
                focusedPrefixColor = text,
                unfocusedPrefixColor = text,
                disabledSuffixColor = text,
                errorSuffixColor = text,
                focusedSuffixColor = text,
                unfocusedSuffixColor = text
            ),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 10.dp,
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            ),
        )

    }
}