package me.sungbin.gitmessengerbot.activity.main.editor.git.model

import com.google.gson.annotations.SerializedName

data class FileContentResponse(
    @field:SerializedName("path")
    val path: String,

    @field:SerializedName("size")
    val size: Int,

    @field:SerializedName("_links")
    val links: Links,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("download_url")
    val downloadUrl: String,

    @field:SerializedName("git_url")
    val gitUrl: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("encoding")
    val encoding: String,

    @field:SerializedName("sha")
    val sha: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("content")
    val content: String
) : GitResultWrapper
