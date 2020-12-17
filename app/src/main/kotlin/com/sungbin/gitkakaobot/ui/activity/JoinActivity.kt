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
import com.sungbin.androidutils.util.DataUtil
import com.sungbin.androidutils.util.PermissionUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.`interface`.GithubInterface
import com.sungbin.gitkakaobot.databinding.ActivityJoinBinding
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.manager.PathManager
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by SungBin on 2020-08-23.
 */

@AndroidEntryPoint
class JoinActivity : AppCompatActivity() {

    private val codeRequestNotificationRead = 3000
    private val codeRequestAccessStorage = 4000

    @Inject
    lateinit var client: Retrofit

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

        checkAllGrantPermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            codeRequestNotificationRead -> {
                if (getNotificationListenerPermission()) {
                    binding.btnRequestNotificationRead.apply {
                        text = getString(R.string.permission_grant)
                        setOnClickListener { }
                        alpha = 0.5f
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
            binding.btnRequestStorage.apply {
                text = getString(R.string.permission_grant)
                setOnClickListener { }
                alpha = 0.5f
            }
            checkAllGrantPermissions()
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

    private fun checkAllGrantPermissions() {
        if (PermissionUtil.checkPermissionsAllGrant(
                applicationContext, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) &&
            getNotificationListenerPermission()
        ) {
            binding.btnRequestNotificationRead.apply {
                text = getString(R.string.permission_grant)
                alpha = 0.5f
                setOnClickListener { }
            }

            binding.btnRequestStorage.apply {
                text = getString(R.string.permission_grant)
                alpha = 0.5f
                setOnClickListener { }
            }

            binding.btnStartWithGithub.apply {
                alpha = 1f
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
                        DataUtil.saveData(
                            applicationContext,
                            PathManager.TOKEN,
                            json["access_token"].asString
                        )
                    }, { throwable ->
                        loadingDialog.setError(Exception(throwable.message))
                    }, {
                        loadingDialog.close()
                        finish()
                        startActivity<DashboardActivity>()
                    })
            }

    }
}