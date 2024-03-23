package com.sharath070.wave.presentation.common.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharath070.wave.presentation.ui.theme.brandColor
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInputField(
    state: MutableState<String>,
    placeHolder: String,
    imeAction: ImeAction,
    maxLines: Int = 1
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = state.value,
        onValueChange = { state.value = it },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        cursorBrush = Brush.verticalGradient(listOf(text, text)),
        maxLines = maxLines,
        textStyle = TextStyle(
            color = text,
            fontFamily = medium,
            fontSize = 14.sp
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = state.value,
            innerTextField = {
                innerTextField()
            },
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = placeHolder,
                    color = textDisabled,
                    style = TextStyle(
                        fontFamily = medium,
                        fontSize = 14.sp
                    )
                )
            },
            colors = TextFieldColors(
                focusedContainerColor = Color.White.copy(0.25f),
                unfocusedContainerColor = Color.White.copy(0.25f),
                disabledContainerColor = Color.White.copy(0.25f),
                errorContainerColor = Color.White.copy(0.25f),
                unfocusedTextColor = text,
                disabledTextColor = text,
                focusedTextColor = text,
                textSelectionColors = TextSelectionColors(
                    backgroundColor = brandColor.copy(0.5f),
                    handleColor = brandColor.copy(0.8f)
                ),
                disabledSupportingTextColor = text,
                errorSupportingTextColor = text,
                errorTextColor = text,
                focusedSupportingTextColor = text,
                unfocusedSupportingTextColor = text,
                cursorColor = brandColor.copy(0.8f),
                errorCursorColor = brandColor.copy(0.8f),
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
                top = 8.dp,
                bottom = 8.dp,
                start = 8.dp,
                end = 8.dp
            ),
        )

    }
}