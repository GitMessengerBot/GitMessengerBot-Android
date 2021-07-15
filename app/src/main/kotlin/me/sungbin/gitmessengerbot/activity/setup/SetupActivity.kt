/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SetupActivity.kt] created by Ji Sungbin on 21. 5. 31. 오후 11:10.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.MainActivity
import me.sungbin.gitmessengerbot.activity.setup.github.model.GithubData
import me.sungbin.gitmessengerbot.activity.setup.github.model.GithubTokenResponse
import me.sungbin.gitmessengerbot.activity.setup.github.model.GithubUserResponse
import me.sungbin.gitmessengerbot.activity.setup.github.repo.GithubRepo
import me.sungbin.gitmessengerbot.activity.setup.github.repo.GithubResult
import me.sungbin.gitmessengerbot.secret.SecretConfig
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.BatteryUtil
import me.sungbin.gitmessengerbot.util.Json
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.Web
import me.sungbin.gitmessengerbot.util.config.StringConfig
import me.sungbin.gitmessengerbot.util.extension.doDelay
import me.sungbin.gitmessengerbot.util.extension.noRippleClickable
import me.sungbin.gitmessengerbot.util.extension.toast

@AndroidEntryPoint
class SetupActivity : ComponentActivity() {

    @Inject
    lateinit var githubRepo: GithubRepo

    private var storagePermissionGranted by mutableStateOf(false)
    private var notificationPermissionGranted by mutableStateOf(false)
    private var batteryPermissionGranted by mutableStateOf(false)

    private val permissionsContracts =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionRequest ->
            if (permissionRequest.values.first()) {
                storagePermissionGranted = true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)

        setContent {
            MaterialTheme {
                Setup()
            }
        }
    }

    @Composable
    private fun Setup() {
        val activity = this@SetupActivity

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
                PermissionView(
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
                PermissionView(
                    permission = Permission(
                        permissions = listOf(PermissionType.NotificationRead),
                        name = stringResource(R.string.setup_permission_notification_label),
                        description = stringResource(R.string.setup_permission_notification_description),
                        icon = R.drawable.ic_round_notifications_24
                    ),
                    permissionGranted = notificationPermissionGranted,
                    padding = PermissionViewPadding(16.dp, 16.dp)
                )
                PermissionView(
                    permission = Permission(
                        permissions = listOf(PermissionType.Battery),
                        name = stringResource(R.string.setup_permission_battery_label),
                        description = stringResource(R.string.setup_permission_battery_description),
                        icon = R.drawable.ic_round_battery_alert_24
                    ),
                    permissionGranted = batteryPermissionGranted,
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
                        .noRippleClickable {
                            if (notificationPermissionGranted && storagePermissionGranted) {
                                Web.open(
                                    applicationContext,
                                    Web.Link.Custom(SecretConfig.GithubOauthAddress)
                                )
                            } else {
                                toast(
                                    activity,
                                    getString(R.string.setup_need_manage_permission)
                                )
                            }
                        },
                    text = stringResource(R.string.setup_start_with_github_login),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }

    private data class PermissionViewPadding(val top: Dp, val bottom: Dp)

    @Composable
    private fun PermissionView(
        permission: Permission,
        permissionGranted: Boolean,
        padding: PermissionViewPadding
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
                    .clickable { permission.requestAllPermissions() }
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
    private fun Permission.requestAllPermissions() {
        val activity = this@SetupActivity

        when (permissions.first()) {
            PermissionType.NotificationRead -> {
                requestReadNotification()
                doDelay(1000) { notificationPermissionGranted = true }
            }
            PermissionType.Battery -> {
                BatteryUtil.requestIgnoreBatteryOptimization(activity)
                doDelay(1000) { batteryPermissionGranted = true }
            }
            PermissionType.ScopedStorage -> {
                Storage.requestStorageManagePermission(activity)
                doDelay(1000) { storagePermissionGranted = true }
            }
            else -> {
                permissionsContracts.launch(this.permissions.toTypedArray())
            }
        }
    }

    private fun requestReadNotification() {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val activity = this@SetupActivity
        val requestCode = intent!!.data!!.getQueryParameter("code")!!

        lifecycleScope.launchWhenCreated {
            githubRepo.getAccessToken(requestCode).collect { accessKeyResult ->
                when (accessKeyResult) {
                    is GithubResult.Success -> {
                        var githubData = GithubData(
                            token = (accessKeyResult.result as GithubTokenResponse).accessToken
                        )
                        githubRepo
                            .getUserInfo(githubData.token)
                            .collect { userResult ->
                                when (userResult) {
                                    is GithubResult.Success -> {
                                        val user = userResult.result as GithubUserResponse

                                        githubData = githubData.copy(
                                            userName = user.login,
                                            profileImageUrl = user.avatarUrl
                                        )

                                        Storage.write(
                                            StringConfig.GithubData,
                                            Json.toString(githubData)
                                        )

                                        finish()
                                        startActivity(
                                            Intent(
                                                activity,
                                                MainActivity::class.java
                                            )
                                        )

                                        toast(
                                            activity,
                                            getString(
                                                R.string.setup_toast_welcome_start,
                                                user.login
                                            )
                                        )
                                    }
                                    is GithubResult.Error -> {
                                        toast(
                                            activity,
                                            getString(
                                                R.string.setup_toast_github_connect_error,
                                                userResult.exception.message
                                            ),
                                            Toast.LENGTH_LONG
                                        )
                                    }
                                }
                            }
                    }
                    is GithubResult.Error -> toast(
                        activity,
                        activity.getString(
                            R.string.setup_toast_github_authorize_error,
                            accessKeyResult.exception.message
                        )
                    )
                }
            }
        }
    }
}
