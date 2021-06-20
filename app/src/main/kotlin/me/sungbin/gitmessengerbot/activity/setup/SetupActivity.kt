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
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.MainActivity
import me.sungbin.gitmessengerbot.repository.github.GithubViewModel
import me.sungbin.gitmessengerbot.repository.github.model.GithubData
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.defaultFontFamily
import me.sungbin.gitmessengerbot.util.PathManager
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.Web
import me.sungbin.gitmessengerbot.util.extension.doDelay
import me.sungbin.gitmessengerbot.util.extension.toast
import me.sungbin.gitmessengerbot.viewmodel.MainViewModel

private object PermissionType {
    const val NotificationRead = "PERMISSION_FOR_NOTIFICATION_READ"
}

private data class Permission(
    val permissions: List<String>,
    val name: String,
    val description: String,
    val painterResource: Int
)

@AndroidEntryPoint
class SetupActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel.instance
    private val githubViewModel: GithubViewModel by viewModels()

    private val isStoragePermissionGranted = mutableStateOf(false)
    private val isNotificationPermissionGranted = mutableStateOf(false)
    private val isPersonalKeyInputDialogOpen = mutableStateOf(false)

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
            MaterialTheme {
                SetupView()
                PersonalKeyInputDialogBind()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun PersonalKeyInputDialogBind() {
        if (isPersonalKeyInputDialogOpen.value) {
            val context = LocalContext.current
            val personalKeyInputField = remember { mutableStateOf(TextFieldValue()) }
            val keyboardController = LocalSoftwareKeyboardController.current
            val activity = this@SetupActivity

            androidx.compose.material.MaterialTheme {
                AlertDialog(
                    shape = RoundedCornerShape(10.dp),
                    properties = DialogProperties(
                        dismissOnClickOutside = false
                    ),
                    onDismissRequest = {
                        isPersonalKeyInputDialogOpen.value = false
                    },
                    text = {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_baseline_circle_24),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(Color.Black),
                                        modifier = Modifier.size(35.dp),
                                    )
                                    Image(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(bottom = 2.dp),
                                        painter = painterResource(R.drawable.ic_round_key_24),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.setup_input_personal_key),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            TextField(
                                maxLines = 1,
                                singleLine = true,
                                modifier = Modifier.padding(top = 10.dp),
                                value = personalKeyInputField.value,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    cursorColor = Color.Black,
                                    textColor = Color.Black,
                                    focusedIndicatorColor = Color.Black
                                ),
                                keyboardActions = KeyboardActions {
                                    keyboardController?.hide()
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                onValueChange = { personalKeyInputField.value = it }
                            )
                            Row(modifier = Modifier.padding(top = 20.dp)) {
                                Text(
                                    text = stringResource(R.string.setup_way_to_get_personal_key),
                                    modifier = Modifier.clickable {
                                        Web.open(this@SetupActivity, Web.Type.PersonalKeyGuide)
                                    },
                                    fontSize = 13.sp,
                                    color = Color.Black
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        text = stringResource(R.string.setup_open_github),
                                        modifier = Modifier.clickable {
                                            Web.open(this@SetupActivity, Web.Type.Github)
                                        },
                                        fontSize = 13.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .clickable {
                                                var githubData =
                                                    GithubData(personalKey = personalKeyInputField.value.text)
                                                githubViewModel.login(
                                                    githubData = githubData,
                                                    onResponse = {
                                                        githubData = githubData.copy(
                                                            userName = login,
                                                            profileImageUrl = avatarUrl
                                                        )

                                                        mainViewModel.githubData = githubData
                                                        Storage.write(
                                                            context,
                                                            PathManager.Storage.GithubData,
                                                            Gson().toJson(githubData)
                                                        )

                                                        finish()
                                                        startActivity(
                                                            Intent(
                                                                activity,
                                                                MainActivity::class.java
                                                            )
                                                        )

                                                        activity.runOnUiThread {
                                                            toast(
                                                                context,
                                                                getString(
                                                                    R.string.setup_welcome_start,
                                                                    login
                                                                )
                                                            )
                                                        }
                                                    },
                                                    onFailure = {
                                                        activity.runOnUiThread {
                                                            toast(
                                                                context,
                                                                getString(
                                                                    R.string.setup_github_connect_error,
                                                                    localizedMessage
                                                                ),
                                                                Toast.LENGTH_LONG
                                                            )
                                                        }
                                                    }
                                                )
                                            },
                                        text = stringResource(R.string.setup_start),
                                        fontSize = 13.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    },
                    buttons = {}
                )
            }
        }
    }

    @Composable
    private fun SetupView() {
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary)
                .padding(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_round_caution_24),
                    modifier = Modifier.size(60.dp),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = with(AnnotatedString.Builder(stringResource(R.string.setup_title))) {
                        addStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = defaultFontFamily
                            ),
                            11, 19 // 아래의 권한들
                        )
                        toAnnotatedString()
                    },
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
                    modifier = if (Storage.isScoped) Modifier.alpha(.5f) else Modifier,
                    permission = Permission(
                        listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        stringResource(R.string.setup_permission_storage_label),
                        stringResource(
                            if (Storage.isScoped) R.string.setup_scoped_storage else R.string.setup_permission_storage_description
                        ),
                        R.drawable.ic_baseline_folder_24
                    ),
                    isPermissionGranted = isStoragePermissionGranted,
                    padding = listOf(0, 16)
                )
                PermissionView(
                    permission = Permission(
                        listOf(PermissionType.NotificationRead),
                        stringResource(R.string.setup_permission_notification_label),
                        stringResource(
                            R.string.setup_permission_notification_description
                        ),
                        R.drawable.ic_baseline_notifications_24
                    ),
                    isPermissionGranted = isNotificationPermissionGranted,
                    padding = listOf(16, 16)
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
                            .clickable {
                                if (Storage.isScoped) {
                                    if (isNotificationPermissionGranted.value) {
                                        isPersonalKeyInputDialogOpen.value = true
                                    } else {
                                        toast(
                                            context,
                                            getString(R.string.setup_need_manage_permission)
                                        )
                                    }
                                } else {
                                    if (isNotificationPermissionGranted.value && isStoragePermissionGranted.value) {
                                        isPersonalKeyInputDialogOpen.value = true
                                    } else {
                                        toast(
                                            context,
                                            getString(R.string.setup_need_manage_permission)
                                        )
                                    }
                                }
                            }
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
        modifier: Modifier = Modifier,
        permission: Permission,
        isPermissionGranted: MutableState<Boolean>,
        padding: List<Int>
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = padding[0].dp, bottom = padding[1].dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, RoundedCornerShape(15.dp))
                    .clickable {
                        if (permission.permissions.first() == PermissionType.NotificationRead) {
                            permission.requestAllPermissions()
                        } else {
                            if (!Storage.isScoped) permission.requestAllPermissions()
                        }
                    }
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /*Box(contentAlignment = Alignment.Center) {
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
                    }*/
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_error_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Text(
                        text = permission.name,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_round_check_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isPermissionGranted.value) colors.primaryVariant else Color.LightGray)
                    )
                }
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
        if (this.permissions.first() == PermissionType.NotificationRead) {
            requestReadNotification(this@SetupActivity)
            doDelay(1000) { isNotificationPermissionGranted.value = true }
        } else permissionsContracts.launch(this.permissions.toTypedArray())

    private fun requestReadNotification(activity: Activity) {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        activity.startActivity(intent)
    }
}
