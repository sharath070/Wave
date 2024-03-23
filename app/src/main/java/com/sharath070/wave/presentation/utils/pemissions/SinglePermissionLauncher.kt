package com.sharath070.wave.presentation.utils.pemissions

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun SinglePermissionLauncher(
    permission: String,
    permissionTextProvider: PermissionTextProvider,
    callback: (Boolean) -> Unit,
    isGrantedForFirstTime: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            openDialog = !isGranted
            isGrantedForFirstTime(isGranted)
        }
    )
    LaunchedEffect(key1 = Unit) {
        permissionResultLauncher.launch(permission)
    }

    if (openDialog) {
        PermissionDialog(
            permissionTextProvider = permissionTextProvider,
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                (context as Activity),
                permission
            ),
            onDismiss = {
                openDialog = false
                callback(false)
            },
            onOkClicked = {
                openDialog = false
                permissionResultLauncher.launch(
                    permission
                )
            },
            onGoToAppSettingsClicked = {
                context.openAppSettings()
                openDialog = false
                callback(false)
            }
        )
    }

}