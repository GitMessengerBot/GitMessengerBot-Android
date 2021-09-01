package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonProperty

data class Commit(
    @field:JsonProperty("committer")
    val committer: Committer? = null,

    @field:JsonProperty("message")
    val message: String? = null,
)
