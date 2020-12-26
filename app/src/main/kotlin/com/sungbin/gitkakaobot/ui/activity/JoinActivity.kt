package com.sungbin.gitkakaobot.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.sungbin.androidutils.extensions.doDelay
import com.sungbin.androidutils.util.*
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.ActivityJoinBinding
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.manager.PathManager
import org.jetbrains.anko.startActivity


/**
 * Created by SungBin on 2020-08-23.
 */

class JoinActivity : AppCompatActivity() {

    private var isBatteryButtonClicked = false
    private val codeRequestNotificationRead = 3000
    private val codeRequestAccessStorage = 4000

    private val loadingDialog by lazy { LoadingDialog(this) }
    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(binding.root)

        binding.btnRequestNotificationRead.setOnClickListener {
            requestNotificationListenerPermission()
        }

        binding.btnRequestStorage.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                codeRequestAccessStorage
            )
        }

        binding.btnRequestBatteryIgnoreOptimization.setOnClickListener {
            BatteryUtil.requestIgnoreBatteryOptimization(applicationContext)
            doDelay(1000) {
                isBatteryButtonClicked = true
                checkAllPermissionsGrant()

                binding.btnRequestBatteryIgnoreOptimization.apply {
                    text = getString(R.string.join_permission_grant)
                    alpha = 0.5f
                    setOnClickListener { }
                }
            }
        }

        checkAllPermissionsGrant()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            codeRequestNotificationRead -> {
                if (getNotificationListenerPermission()) {
                    binding.btnRequestNotificationRead.apply {
                        text = getString(R.string.join_permission_grant)
                        setOnClickListener { }
                        alpha = 0.5f
                    }
                }
                checkAllPermissionsGrant()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {
            binding.btnRequestStorage.apply {
                text = getString(R.string.join_permission_grant)
                setOnClickListener { }
                alpha = 0.5f
            }
            checkAllPermissionsGrant()
        }
    }

    private fun getNotificationListenerPermission() =
        NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)

    private fun requestNotificationListenerPermission() {
        startActivityForResult(
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"),
            codeRequestNotificationRead
        )
    }

    private fun checkAllPermissionsGrant() {
        if (PermissionUtil.checkPermissionsAllGrant(
                applicationContext, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) && getNotificationListenerPermission() && isBatteryButtonClicked
        ) {
            binding.btnRequestNotificationRead.apply {
                text = getString(R.string.join_permission_grant)
                alpha = 0.5f
                setOnClickListener { }
            }

            binding.btnRequestStorage.apply {
                text = getString(R.string.join_permission_grant)
                alpha = 0.5f
                setOnClickListener { }
            }

            binding.btnStartWithGithub.apply {
                alpha = 1f
                setOnClickListener {
                    val view = View.inflate(context, R.layout.layout_access_key_dialog, null)
                    val editText = view.findViewById<EditText>(R.id.et_access_key)
                    val dialog = AlertDialog.Builder(context)
                    dialog.setView(view)
                    dialog.setPositiveButton(context.getString(R.string.save)) { _, _ ->
                        val name = editText.text!!.toString()
                        if (name.isBlank()) {
                            ToastUtil.show(
                                context,
                                context.getString(R.string.join_personal_key_is_empty),
                                ToastLength.SHORT,
                                ToastType.WARNING
                            )
                        } else {
                            ToastUtil.show(
                                context,
                                context.getString(R.string.join_saved_personal_key),
                                ToastLength.SHORT,
                                ToastType.SUCCESS
                            )
                            DataUtil.saveData(context, PathManager.TOKEN, name)
                            finish()
                            startActivity<DashboardActivity>()
                        }
                    }
                    dialog.setNegativeButton(context.getString(R.string.join_open_github)) { _, _ ->
                        val builder = CustomTabsIntent.Builder().build()
                        builder.launchUrl(
                            context,
                            "https://github.com/".toUri()
                        )
                    }
                    dialog.setNeutralButton(context.getString(R.string.join_way_to_get_personal_key)) { _, _ ->
                        val builder = CustomTabsIntent.Builder().build()
                        builder.launchUrl(
                            context,
                            "https://github.com/sungbin5304/GitMessengerBot/blob/master/get-personal-access-key.md".toUri()
                        )
                    }
                    dialog.show()
                }
            }

        }
    }
}