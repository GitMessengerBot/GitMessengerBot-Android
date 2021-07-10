/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepoImpl.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:15.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup.github.repo

import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import me.sungbin.gitmessengerbot.activity.setup.github.GithubService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.await

class GithubRepoImpl @Inject constructor(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val retrofit: Retrofit.Builder
) : GithubRepo {

    private class AuthInterceptor(private val token: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            return try {
                builder = builder
                    .addHeader("Authorization", "token $token")
                    .addHeader("Accept", "application/json")
                chain.proceed(builder.build())
            } catch (ignored: Exception) {
                chain.proceed(builder.build())
            }
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun buildRetrofit(token: String) = retrofit
        .client(getInterceptor(httpLoggingInterceptor, AuthInterceptor(token)))
        .build()
        .create(GithubService::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun login(githubKey: String) = callbackFlow {
        try {
            trySend(GithubRepoResult.Success(buildRetrofit(githubKey).getUserInfo().await()))
        } catch (exception: Exception) {
            trySend(GithubRepoResult.Error(exception))
        }
        awaitClose { close() }
    }

    override fun commit() {
        // todo
    }

    override fun merge() {
        // todo
    }
}
