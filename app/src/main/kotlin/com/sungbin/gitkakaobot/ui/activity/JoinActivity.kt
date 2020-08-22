package com.sungbin.gitkakaobot.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.`interface`.GithubInterface
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_join.*
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by SungBin on 2020-08-23.
 */

@AndroidEntryPoint
class JoinActivity : AppCompatActivity() {

    private val CODE_REQUEST_NOTIFICATION_READ = 3000
    private val CODE_REQUEST_STORAGE_ACCESS = 3001

    @Inject
    lateinit var client: Retrofit

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_join)

        btn_request_notification_read.setOnClickListener {
            requestNotificationListenerPermission()
        }

        btn_request_storage.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                CODE_REQUEST_STORAGE_ACCESS
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODE_REQUEST_NOTIFICATION_READ -> {
                if (getNotificationListenerPermission()) {
                    btn_request_notification_read.apply {
                        text = getString(R.string.permission_grant)
                        alpha = 0.5f
                        isEnabled = false
                    }
                }
                checkAllGrantPermissions()
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
            btn_request_storage.apply {
                text = getString(R.string.permission_grant)
                alpha = 0.5f
                isEnabled = false
            }
            checkAllGrantPermissions()
        }
    }

    private fun getNotificationListenerPermission() =
        NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)

    private fun requestNotificationListenerPermission() {
        startActivityForResult(
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"),
            CODE_REQUEST_NOTIFICATION_READ
        )
    }

    private fun checkAllGrantPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED &&
            getNotificationListenerPermission()
        ) {
            btn_start_with_github.apply {
                alpha = 1.0f
                isEnabled = true
                setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(
                        context,
                        Uri.parse("https://github.com/login/oauth/authorize?client_id=${getString(R.string.github_client_id)}")
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code").toString()
        client
            .create(GithubInterface::class.java).run {
                loadingDialog.show()

                getAuthCode(
                    getString(R.string.github_client_id),
                    getString(R.string.github_client_secret),
                    code
                )
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ json ->
                        UiUtil.toast(applicationContext, json["access_token"].asString)
                    }, { throwable ->
                        loadingDialog.setError(throwable)
                    }, {
                        loadingDialog.close()
                    })
            }

    }
}