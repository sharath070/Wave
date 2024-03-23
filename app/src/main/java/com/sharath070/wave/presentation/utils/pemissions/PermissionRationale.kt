package com.sharath070.wave.presentation.utils.pemissions

import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.layer
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined:Boolean,
    onDismiss: () -> Unit,
    onOkClicked: () ->Unit,
    onGoToAppSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(color = layer)
                Text(
                    text = if (isPermanentlyDeclined) "Grant permission"
                    else "Okay",
                    textAlign = TextAlign.Center,
                    fontFamily = medium,
                    color = text,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable {
                            if (isPermanentlyDeclined) onGoToAppSettingsClicked()
                            else onOkClicked()
                        }
                )
            }
        },
        title = {
            Text(
                text = "Permission Required",
                fontFamily = medium,
                color = text,
                fontSize = 22.sp
            )
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDeclined),
                fontSize = 16.sp,
                fontFamily = regular,
                color = textDisabled
            )
        },
        containerColor = bg
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class PostNotificationPermissionProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined notification permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app requires notification permissions to control music playback even " +
                    "when the app is in the background."
        }
    }
}