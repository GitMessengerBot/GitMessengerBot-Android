/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepositoryImpl.kt] created by Ji Sungbin on 21. 8. 13. 오후 7:32.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.repo

import io.github.jisungbin.gitmessengerbot.common.extension.runIf
import io.github.jisungbin.gitmessengerbot.data.github.api.GithubUserService
import io.github.jisungbin.gitmessengerbot.data.github.mapper.toDomain
import io.github.jisungbin.gitmessengerbot.data.github.secret.SecretConfig
import io.github.jisungbin.gitmessengerbot.data.github.util.isValid
import io.github.jisungbin.gitmessengerbot.data.github.util.toFailResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubUser
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class GithubUserRepositoryImpl(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val userRetrofit: Retrofit.Builder,
    private val aouthRetrofit: Retrofit.Builder,
    private val coroutineScope: CoroutineScope
) : GithubUserRepository {
    private class AuthInterceptor(private val aouthToken: String?) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .runIf(aouthToken != null) { addHeader("Authorization", "token $aouthToken") }
            return chain.proceed(builder.build())
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun getApi(retrofit: Retrofit.Builder, aouthToken: String?) = retrofit
        .client(getInterceptor(httpLoggingInterceptor, AuthInterceptor(aouthToken)))
        .build()
        .create(GithubUserService::class.java)

    override suspend fun getUserInfo(aouthToken: String): Result<GithubUser> =
        suspendCoroutine { continuation ->
            coroutineScope.launch {
                try {
                    val request =
                        getApi(retrofit = userRetrofit, aouthToken = aouthToken).getUserInfo()
                    continuation.resume(
                        if (request.isValid()) {
                            Result.success(request.body()!!.toDomain())
                        } else {
                            request.toFailResult("getUserInfo")
                        }
                    )
                } catch (exception: Exception) {
                    continuation.resume(Result.failure(exception))
                }
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun requestAouthToken(requestCode: String): Result<GithubAouth> =
        suspendCoroutine { continuation ->
            coroutineScope.launch {
                try {
                    val request =
                        getApi(retrofit = aouthRetrofit, aouthToken = null).requestAouthToken(
                            requestCode = requestCode,
                            clientId = SecretConfig.GithubOauthClientId,
                            clientSecret = SecretConfig.GithubOauthClientSecret
                        )
                    continuation.resume(
                        if (request.isValid()) {
                            Result.success(request.body()!!.toDomain())
                        } else {
                            request.toFailResult("requestAouthToken")
                        }
                    )
                } catch (exception: Exception) {
                    continuation.resume(Result.failure(exception))
                }
            }
        }
}
