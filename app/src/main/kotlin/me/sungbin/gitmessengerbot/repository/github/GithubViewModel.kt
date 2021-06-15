/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubViewModel.kt] created by Ji Sungbin on 21. 6. 15. 오후 9:50.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repository.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import me.sungbin.gitmessengerbot.repository.github.model.GithubData
import me.sungbin.gitmessengerbot.util.LoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val loggingInterceptor: LoggingInterceptor,
    private val retrofit: Retrofit.Builder
) : ViewModel() {

    private class AuthInterceptor(private val token: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
                .addHeader("Authorization", "token $token")
                .addHeader("Accept", "application/json")
            return chain.proceed(builder.build())
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun buildRetrofit(token: String) = retrofit
        .client(getInterceptor(loggingInterceptor.instance, AuthInterceptor(token)))
        .build()
        .create(GithubService::class.java)

    fun login(githubData: GithubData) = viewModelScope.launch {
    }
}
