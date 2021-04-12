/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sungbin.androidutils.extensions.doDelay
import me.sungbin.androidutils.util.PermissionUtil
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.BindView
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors

/**
 * Created by SungBin on 2021/04/08.
 */

class SetupActivity : ComponentActivity() {

    data class Permission(
        val permissions: List<String>,
        val name: String,
        val description: String,
        val painterResource: Int
    )

    val isStoragePermissionGranted = mutableStateOf(false)
    val isNotificationPermissionGranted = mutableStateOf(false)

    private val permissionsContracts =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionRequest ->
            if (permissionRequest.values.first()) {
                isStoragePermissionGranted.value = true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)

        setContent {
            BindView {
                SetupView()
            }
        }
    }

    @Preview
    @Composable
    private fun SetupView() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary)
                .padding(30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_round_warning_amber_24),
                    modifier = Modifier.size(80.dp),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.setup_title),
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PermissionView(
                    Permission(
                        listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        stringResource(R.string.setup_permission_storage_label),
                        stringResource(
                            R.string.setup_permission_storage_description
                        ),
                        R.drawable.ic_baseline_folder_24
                    ),
                    isStoragePermissionGranted,
                    listOf(0, 16)
                )
                PermissionView(
                    Permission(
                        listOf(PERMISSION_NOTIFICATION_READ),
                        stringResource(R.string.setup_permission_notification_label),
                        stringResource(
                            R.string.setup_permission_notification_description
                        ),
                        R.drawable.ic_baseline_notifications_24
                    ),
                    isNotificationPermissionGranted,
                    listOf(16, 16)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(R.string.setup_last_func),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Column(
                    modifier = Modifier.background(
                        color = colors.primaryVariant,
                        RoundedCornerShape(15.dp)
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = stringResource(R.string.setup_start_with_personal_key),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    @Composable
    private fun PermissionView(
        permission: Permission,
        isPermissionGrant: MutableState<Boolean>,
        padding: List<Int>
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (isPermissionGrant.value) 0.5f else 1f)
                .padding(top = padding[0].dp, bottom = padding[1].dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, RoundedCornerShape(15.dp))
                    .clickable {
                        permission.requestAllPermissions()
                    }
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_circle_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Image(
                        modifier = Modifier.size(15.dp),
                        painter = painterResource(permission.painterResource),
                        contentDescription = null
                    )
                }
                Text(
                    text = permission.name,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = Color.White,
                text = permission.description,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
    }

    private fun Permission.requestAllPermissions() =
        if (this.permissions.first() == PERMISSION_NOTIFICATION_READ) {
            PermissionUtil.requestReadNotification(this@SetupActivity)
            doDelay(1000) {
                isNotificationPermissionGranted.value = true
            }
        } else permissionsContracts.launch(this.permissions.toTypedArray())

    companion object {
        const val PERMISSION_NOTIFICATION_READ = "PERMISSION_FOR_NOTIFICATION_READ"
    }
}
