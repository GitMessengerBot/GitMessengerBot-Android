package com.sungbin.gitkakaobot.`interface`

import io.reactivex.rxjava3.core.Flowable
import okhttp3.ResponseBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


interface BeautifyInterface {

    @FormUrlEncoded
    @POST("raw")
    fun requestMinify(
        @Query("input") input: String
    ): Flowable<ResponseBody>

}