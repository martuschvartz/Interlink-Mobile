package com.example.interlink

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.interlink.ui.InterlinkApp
import com.example.interlink.receiver.SkipNotificationReceiver
import com.example.interlink.ui.theme.InterlinkTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {

    private lateinit var receiver: SkipNotificationReceiver

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InterlinkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permissionState =
                            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                        if (!permissionState.status.isGranted) {
                            Column {
                                val textToShow = if (permissionState.status.shouldShowRationale) {
                                    // If the user has denied the permission but the rationale can be shown,
                                    // then gently explain why the app requires this permission
                                    "Notifications are important for this app. Please grant the permission."
                                } else {
                                    // If it's the first time the user lands on this feature, or the user
                                    // doesn't want to be asked again for this permission, explain that the
                                    // permission is required
                                    "Notification permission required for this feature to be available. " +
                                            "Please grant the permission"
                                }
                                Text(textToShow)

                                LaunchedEffect(true) {
                                    permissionState.launchPermissionRequest()
                                }
                            }
                        }
                    }

                    val deviceId = intent?.getStringExtra(MyIntent.EVENT_DATA)
                    if (deviceId != null) {
                        Text(
                            text = "$deviceId"
                        )
                    }
                }
            }

            InterlinkApp()
        }


    }
}

@Composable
fun MainActivityPreview(){
    InterlinkApp()
}



