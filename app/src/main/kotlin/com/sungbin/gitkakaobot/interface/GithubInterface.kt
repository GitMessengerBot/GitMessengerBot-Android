package com.sungbin.gitkakaobot.`interface`

import com.google.gson.JsonObject
import com.sungbin.gitkakaobot.model.Repo
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Path


/**
 * Created by SungBin on 2020-08-23.
 */

interface GithubInterface {

    // 헤더 고정 ::: 다른 해더 쓰면 오류

    @PATCH("repos/{owner}/{repo}")
    @Headers("Accept: application/json")
    fun updateRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body body: Repo
    ): Flowable<JsonObject>

}