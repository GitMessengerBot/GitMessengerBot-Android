/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepositoryImpl.kt] created by Ji Sungbin on 21. 8. 13. 오후 7:32.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.repo

import io.github.jisungbin.gitmessengerbot.common.exception.DataGithubException
import io.github.jisungbin.gitmessengerbot.data.github.api.GithubUserService
import io.github.jisungbin.gitmessengerbot.data.github.mapper.toDomain
import io.github.jisungbin.gitmessengerbot.data.github.secret.SecretConfig
import io.github.jisungbin.gitmessengerbot.data.github.util.isValid
import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class GithubUserRepositoryImpl(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val userRetrofit: Retrofit.Builder,
    private val aouthRetrofit: Retrofit.Builder,
) : GithubUserRepository {

    private class AuthInterceptor(private val token: String?) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            return try {
                builder = builder.addHeader("Accept", "application/json")
                if (token != null) builder = builder.addHeader("Authorization", "token $token")
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

    private fun buildRetrofit(retrofit: Retrofit.Builder, token: String?) = retrofit
        .client(getInterceptor(httpLoggingInterceptor, AuthInterceptor(token)))
        .build()
        .create(GithubUserService::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserInfo(githubKey: String) = callbackFlow {
        try {
            val request = buildRetrofit(userRetrofit, githubKey).getUserInfo()
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(request.body()!!.toDomain())
                } else {
                    GithubResult.Fail(DataGithubException("Github.getUserInfo() response is null."))
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(DataGithubException(exception.message)))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun requestAouthToken(requestCode: String) = callbackFlow {
        try {
            val request = buildRetrofit(userRetrofit, null).requestAouthToken(
                requestCode,
                SecretConfig.GithubOauthClientId,
                SecretConfig.GithubOauthClientSecret
            )
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(request.body()!!.toDomain())
                } else {
                    GithubResult.Fail(DataGithubException("Github.requestAouthToken() response is null."))
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(DataGithubException(exception.message)))
        }

        awaitClose { close() }
    }
}
