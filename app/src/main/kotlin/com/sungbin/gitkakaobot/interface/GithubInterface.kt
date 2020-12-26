package com.sungbin.gitkakaobot.`interface`

import com.google.gson.JsonObject
import com.sungbin.gitkakaobot.model.Repo
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.*


/**
 * Created by SungBin on 2020-08-23.
 */

interface GithubInterface {

    // 헤더 고정 ::: 다른 해더 쓰면 오류

    @FormUrlEncoded
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    fun getAuthCode(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Flowable<JsonObject>

    @PATCH("repos/{owner}/{repo}")
    @Headers("Accept: application/json")
    fun updateRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body body: Repo
    ): Flowable<JsonObject>


}