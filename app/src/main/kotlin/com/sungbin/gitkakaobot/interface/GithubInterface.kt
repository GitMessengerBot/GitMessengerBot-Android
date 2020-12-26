package com.sungbin.gitkakaobot.`interface`

import com.google.gson.JsonObject
import com.sungbin.gitkakaobot.model.github.File
import com.sungbin.gitkakaobot.model.github.Repo
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.*


/**
 * Created by SungBin on 2020-08-23.
 */

interface GithubInterface {

    // 헤더 고정 ::: 다른 해더 쓰면 오류

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    @Headers("Accept: application/json")
    fun updateFile(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Body body: File
    ): Flowable<JsonObject>

    @POST("/user/repos")
    @Headers("Accept: application/json")
    fun createRepo(
        @Body body: Repo
    ): Flowable<JsonObject>

}