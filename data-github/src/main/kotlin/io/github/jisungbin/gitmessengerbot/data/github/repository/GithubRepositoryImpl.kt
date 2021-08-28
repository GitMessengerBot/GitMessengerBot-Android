/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepositoryImpl.kt] created by Ji Sungbin on 21. 8. 13. 오후 7:32.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.repository

import io.github.jisungbin.gitmessengerbot.data.github.api.GithubAouthService
import io.github.jisungbin.gitmessengerbot.data.github.api.GithubUserService
import io.github.jisungbin.gitmessengerbot.data.github.secret.SecretConfig
import io.github.jisungbin.gitmessengerbot.data.util.DataException
import io.github.jisungbin.gitmessengerbot.domain.github.repository.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class GithubRepositoryImpl(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val userRetrofit: Retrofit.Builder,
    private val aouthRetrofit: Retrofit.Builder
) : GithubRepository {

    private class AuthInterceptor(private val token: String?) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            return try {
                builder = builder.addHeader("Accept", "application/json")
                if (token != null) {
                    builder = builder.addHeader("Authorization", "token $token")
                }
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

    private fun <T> buildRetrofit(retrofit: Retrofit.Builder, token: String?, service: Class<T>) =
        retrofit
            .client(getInterceptor(httpLoggingInterceptor, AuthInterceptor(token)))
            .build()
            .create(service)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserInfo(githubKey: String) = callbackFlow {
        try {
            val request = buildRetrofit(
                userRetrofit,
                githubKey,
                GithubUserService::class.java
            ).getUserInfo()
            trySend(
                if (request.isSuccessful && request.body() != null) {
                    Result.Success(request.body()!!.toDomain())
                } else {
                    Result.Fail(DataException("GithubRepository.getUserInfo"))
                }
            )
        } catch (exception: Exception) {
            trySend(Result.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun requestAouthToken(requestCode: String) = callbackFlow {
        try {
            val request = buildRetrofit(
                aouthRetrofit,
                null,
                GithubAouthService::class.java
            ).requestAouthToken(
                requestCode,
                SecretConfig.GithubOauthClientId,
                SecretConfig.GithubOauthClientSecret
            )
            trySend(
                if (request.isSuccessful && request.body() != null) {
                    Result.Success(request.body()!!.toDomain())
                } else {
                    Result.Fail(DataException("GithubRepository.requestAouthToken"))
                }
            )
        } catch (exception: Exception) {
            trySend(Result.Fail(exception))
        }

        awaitClose { close() }
    }
}
