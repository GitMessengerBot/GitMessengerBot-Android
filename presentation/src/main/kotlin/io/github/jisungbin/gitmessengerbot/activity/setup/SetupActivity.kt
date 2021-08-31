/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SetupActivity.kt] created by Ji Sungbin on 21. 5. 31. 오후 11:10.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.home.main.MainActivity
import io.github.jisungbin.gitmessengerbot.common.core.NotificationUtil
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.core.Wear
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.doDelay
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.data.github.secret.SecretConfig
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.util.doWhen
import io.github.jisungbin.gitmessengerbot.util.extension.noRippleClickable
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SetupActivity : ComponentActivity() {

    private val vm: SetupViewModel by viewModels()

    private var wearAppInstalled by mutableStateOf(false)
    private var storagePermissionGranted by mutableStateOf(false)
    private var notificationPermissionGranted by mutableStateOf(false)

    private val permissionsContracts =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionRequest ->
            if (permissionRequest.values.first()) {
                storagePermissionGranted = true
            }
        }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wearAppInstalled = Wear.checkInstalled(applicationContext)
        notificationPermissionGranted =
            NotificationUtil.isNotificationListenerPermissionGranted(applicationContext)

        storagePermissionGranted = if (Storage.isScoped) {
            Storage.isStorageManagerPermissionGranted()
        } else {
            ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }

        SystemUiController(window).setSystemBarsColor(colors.primary)

        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary)
                .padding(30.dp)
        ) {
            val (header, content, footer) = createRefs()

            Column(
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_caution_24),
                    modifier = Modifier.size(60.dp),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = with(AnnotatedString.Builder(stringResource(R.string.setup_title))) {
                        addStyle(SpanStyle(fontWeight = FontWeight.Bold), 11, 19) // 아래의 권한들
                        toAnnotatedString()
                    },
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(header.bottom)
                    bottom.linkTo(footer.top)
                    height = Dimension.fillToConstraints
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PermissionDisplay(
                    permission = Permission(
                        permissions = if (Storage.isScoped) {
                            listOf(PermissionType.ScopedStorage)
                        } else {
                            listOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        },
                        name = stringResource(R.string.setup_permission_storage_label),
                        description = stringResource(R.string.setup_permission_storage_description),
                        icon = R.drawable.ic_round_folder_24
                    ),
                    permissionGranted = storagePermissionGranted,
                    padding = PermissionViewPadding(0.dp, 16.dp)
                )
                PermissionDisplay(
                    permission = Permission(
                        permissions = listOf(PermissionType.NotificationRead),
                        name = stringResource(R.string.setup_permission_notification_label),
                        description = stringResource(R.string.setup_permission_notification_description),
                        icon = R.drawable.ic_round_notifications_24
                    ),
                    permissionGranted = notificationPermissionGranted,
                    padding = PermissionViewPadding(16.dp, 16.dp)
                )
                PermissionDisplay(
                    permission = Permission(
                        permissions = listOf(PermissionType.Wear),
                        name = stringResource(R.string.setup_app_wear_label),
                        description = stringResource(R.string.setup_app_wear_description),
                        icon = R.drawable.ic_round_watch_24
                    ),
                    permissionGranted = wearAppInstalled,
                    padding = PermissionViewPadding(16.dp, 0.dp)
                )
            }
            Column(
                modifier = Modifier.constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val shape = RoundedCornerShape(15.dp)

                Text(
                    text = stringResource(R.string.setup_last_func),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape)
                        .background(
                            color = colors.primaryVariant,
                            shape = shape
                        )
                        .padding(8.dp)
                        .noRippleClickable(onClick = {
                            if (notificationPermissionGranted && storagePermissionGranted) {
                                Web.open(
                                    applicationContext,
                                    Web.Link.Custom(SecretConfig.GithubOauthAddress)
                                )
                            } else {
                                toast(getString(R.string.setup_need_manage_permission))
                            }
                        }),
                    text = stringResource(R.string.setup_start_with_github_login),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }

    @Composable
    private fun PermissionDisplay(
        permission: Permission,
        permissionGranted: Boolean,
        padding: PermissionViewPadding,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = padding.top, bottom = padding.bottom),
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .clickable { permission.requestPermission() }
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                val (icon, name, granted) = createRefs()

                Icon(
                    painter = painterResource(permission.icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
                Text(
                    text = permission.name,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(name) {
                        start.linkTo(icon.end, 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
                Icon(
                    painter = painterResource(R.drawable.ic_round_check_24),
                    contentDescription = null,
                    tint = if (permissionGranted) colors.primaryVariant else Color.LightGray,
                    modifier = Modifier.constrainAs(granted) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                color = Color.White,
                text = permission.description,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
    }

    @SuppressLint("NewApi")
    private fun Permission.requestPermission() {
        when (permissions.first()) {
            PermissionType.NotificationRead -> {
                NotificationUtil.requestNotificationListenerPermission(this@SetupActivity)
                doDelay(1000) {
                    notificationPermissionGranted = true
                }
            }
            PermissionType.Wear -> {
                Wear.install(applicationContext)
                doDelay(1000) {
                    wearAppInstalled = true
                }
            }
            PermissionType.ScopedStorage -> {
                Storage.requestStorageManagePermission(this@SetupActivity)
                doDelay(1000) {
                    storagePermissionGranted = true
                }
            }
            else -> {
                permissionsContracts.launch(this.permissions.toTypedArray())
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val requestCode = intent?.data?.getQueryParameter("code")
            ?: throw PresentationException("Github aouth request code intent data is null.")

        lifecycleScope.launchWhenCreated {
            vm.login(requestCode).collect { loginResult ->
                loginResult.doWhen(
                    onSuccess = { githubData ->
                        finish()
                        startActivity(Intent(this@SetupActivity, MainActivity::class.java))
                        toast(getString(R.string.vm_setup_toast_welcome_start, githubData.userName))
                    },
                    onFail = { exception ->
                        println(exception.message)
                        // todo: ErrorDialog
                    }
                )
            }
        }
    }
}
