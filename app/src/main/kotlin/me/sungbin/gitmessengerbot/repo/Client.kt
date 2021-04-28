/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repo

import me.sungbin.gitmessengerbot.repo.github.GithubService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private var token = ""
    private lateinit var githubClient: GithubService
    private const val BaseUrl = "https://api.github.com"

    private class AuthInterceptor(private val token: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
                .addHeader("Authorization", "token $token")
                .addHeader("Accept", "application/json")
            return chain.proceed(builder.build())
        }
    }

    private val provideLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    fun github(token: String): GithubService {
        if (!::githubClient.isInitialized || this.token != token) {
            this.token = token
            githubClient = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(provideLoggingInterceptor, AuthInterceptor(token)))
                .baseUrl(BaseUrl)
                .build()
                .create(GithubService::class.java)
        }
        return githubClient
    }
}
