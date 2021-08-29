/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RetrofitModule.kt] created by Ji Sungbin on 21. 8. 29. 오후 6:49
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup.repo.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.jisungbin.gitmessengerbot.activity.setup.repo.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.activity.setup.repo.qualifier.UserRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object RetrofitModule {
    private fun buildRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @UserRetrofit
    @ActivityScoped
    fun provideUserRetrofit() = buildRetrofit("https://api.github.com")

    @Provides
    @AouthRetrofit
    @ActivityScoped
    fun provideAouthRetrofit() = buildRetrofit("https://github.com")
}
