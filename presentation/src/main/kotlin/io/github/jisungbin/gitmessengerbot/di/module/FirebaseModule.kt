// ktlint-disable filename
/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [FirebaseModule.kt] created by Ji Sungbin on 21. 11. 28. 오후 10:59
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.di.module

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import java.util.Date

@Module
@InstallIn(ActivityComponent::class)
object ActivityFirebaseModule {
    @Provides
    @ActivityScoped
    fun provideExceptionFirestoreDocument(): DocumentReference = Firebase.firestore
        .collection("exception")
        .document(Date().toString())
}
