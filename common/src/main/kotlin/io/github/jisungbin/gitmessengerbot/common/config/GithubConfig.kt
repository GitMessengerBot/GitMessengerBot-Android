package io.github.jisungbin.gitmessengerbot.common.config

object GithubConfig {
    const val DataPath = "${PathConfig.AppStorage}/github-data.json"

    const val DefaultBranch = "main"
    const val DefaultRepoDescription = "Created by GitMessengerBot"
    const val DefaultCommitMessage = "Commited by GitMessengerBot" // TODO: pattern
}
