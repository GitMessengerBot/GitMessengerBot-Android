package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sungbin.androidutils.util.StorageUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.ActivityDashboardBinding
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.manager.PathManager
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by SungBin on 2020-08-23.
 */

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    companion object {
        lateinit var onBackPressedAction: () -> Unit

        // todo: viewmodel 쓰기
        val botList: MutableLiveData<ArrayList<Bot>> = MutableLiveData()
        fun initBotList() {
            if (botList.value?.isEmpty() != false) botList.value = BotUtil.botItems
        }
    }

    private lateinit var navController: NavController
    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_container) as NavHostFragment
        navController = navHostFragment.navController

        StorageUtil.createFolder(PathManager.JS)
        StorageUtil.createFolder(PathManager.SIMPLE)
        StorageUtil.createFolder(PathManager.DATABASE)
        StorageUtil.createFolder(PathManager.LOG)

        supportActionBar?.hide()

        /*client
            .create(GithubInterface::class.java).run {
                fun log(message: String) {
                    Logger.w("github", message)
                }
                log("시작됨")

                val json = JSONObject()
                json.put("name", "YEEEEEEEEEEEEEEEEEE")

                updateRepo(
                    "sungbin5304",
                    "test1",
                    Repo("aaaaaaaaaaa")
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ json ->
                        log(json.toString())
                    }, { throwable ->
                        log(throwable.toString())
                    }, {
                        log("끝")
                    })
            }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        binding.sbbNavigation.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    override fun onBackPressed() {
        onBackPressedAction()
    }

}