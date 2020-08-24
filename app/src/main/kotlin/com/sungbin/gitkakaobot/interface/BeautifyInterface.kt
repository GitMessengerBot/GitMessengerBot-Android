package com.sungbin.gitkakaobot.`interface`

import io.reactivex.rxjava3.core.Flowable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface BeautifyInterface {

    @Multipart
    @Headers("content-type:application/x-www-form-urlencoded")
    @POST("raw")
    fun requestMinify(
        @Part("input") input: RequestBody
    ): Flowable<ResponseBody>

}