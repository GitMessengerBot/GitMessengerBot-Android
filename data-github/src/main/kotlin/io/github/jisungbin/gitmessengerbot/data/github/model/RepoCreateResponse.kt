/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RepoCreateResponse.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RepoCreateResponse(
    @field:JsonProperty("stargazers_count")
    val stargazersCount: Int?,

    @field:JsonProperty("pushed_at")
    val pushedAt: String?,

    @field:JsonProperty("subscription_url")
    val subscriptionUrl: String?,

    @field:JsonProperty("language")
    val language: Any?,

    @field:JsonProperty("branches_url")
    val branchesUrl: String?,

    @field:JsonProperty("issue_comment_url")
    val issueCommentUrl: String?,

    @field:JsonProperty("allow_rebase_merge")
    val allowRebaseMerge: Boolean?,

    @field:JsonProperty("labels_url")
    val labelsUrl: String?,

    @field:JsonProperty("subscribers_url")
    val subscribersUrl: String?,

    @field:JsonProperty("permissions")
    val permissions: Permissions?,

    @field:JsonProperty("releases_url")
    val releasesUrl: String?,

    @field:JsonProperty("svn_url")
    val svnUrl: String?,

    @field:JsonProperty("subscribers_count")
    val subscribersCount: Int?,

    @field:JsonProperty("id")
    val id: Int?,

    @field:JsonProperty("forks")
    val forks: Int?,

    @field:JsonProperty("archive_url")
    val archiveUrl: String?,

    @field:JsonProperty("allow_merge_commit")
    val allowMergeCommit: Boolean?,

    @field:JsonProperty("git_refs_url")
    val gitRefsUrl: String?,

    @field:JsonProperty("forks_url")
    val forksUrl: String?,

    @field:JsonProperty("statuses_url")
    val statusesUrl: String?,

    @field:JsonProperty("network_count")
    val networkCount: Int?,

    @field:JsonProperty("ssh_url")
    val sshUrl: String?,

    @field:JsonProperty("license")
    val license: Any?,

    @field:JsonProperty("full_name")
    val fullName: String?,

    @field:JsonProperty("size")
    val size: Int?,

    @field:JsonProperty("languages_url")
    val languagesUrl: String?,

    @field:JsonProperty("html_url")
    val htmlUrl: String?,

    @field:JsonProperty("collaborators_url")
    val collaboratorsUrl: String?,

    @field:JsonProperty("clone_url")
    val cloneUrl: String?,

    @field:JsonProperty("name")
    val name: String?,

    @field:JsonProperty("pulls_url")
    val pullsUrl: String?,

    @field:JsonProperty("default_branch")
    val defaultBranch: String?,

    @field:JsonProperty("hooks_url")
    val hooksUrl: String?,

    @field:JsonProperty("trees_url")
    val treesUrl: String?,

    @field:JsonProperty("tags_url")
    val tagsUrl: String?,

    @field:JsonProperty("private")
    val jsonMemberPrivate: Boolean?,

    @field:JsonProperty("contributors_url")
    val contributorsUrl: String?,

    @field:JsonProperty("has_downloads")
    val hasDownloads: Boolean?,

    @field:JsonProperty("notifications_url")
    val notificationsUrl: String?,

    @field:JsonProperty("open_issues_count")
    val openIssuesCount: Int?,

    @field:JsonProperty("description")
    val description: String?,

    @field:JsonProperty("created_at")
    val createdAt: String?,

    @field:JsonProperty("watchers")
    val watchers: Int?,

    @field:JsonProperty("keys_url")
    val keysUrl: String?,

    @field:JsonProperty("deployments_url")
    val deploymentsUrl: String?,

    @field:JsonProperty("has_projects")
    val hasProjects: Boolean?,

    @field:JsonProperty("archived")
    val archived: Boolean?,

    @field:JsonProperty("has_wiki")
    val hasWiki: Boolean?,

    @field:JsonProperty("updated_at")
    val updatedAt: String?,

    @field:JsonProperty("comments_url")
    val commentsUrl: String?,

    @field:JsonProperty("stargazers_url")
    val stargazersUrl: String?,

    @field:JsonProperty("disabled")
    val disabled: Boolean?,

    @field:JsonProperty("delete_branch_on_merge")
    val deleteBranchOnMerge: Boolean?,

    @field:JsonProperty("git_url")
    val gitUrl: String?,

    @field:JsonProperty("has_pages")
    val hasPages: Boolean?,

    @field:JsonProperty("owner")
    val owner: Owner?,

    @field:JsonProperty("allow_squash_merge")
    val allowSquashMerge: Boolean?,

    @field:JsonProperty("commits_url")
    val commitsUrl: String?,

    @field:JsonProperty("compare_url")
    val compareUrl: String?,

    @field:JsonProperty("git_commits_url")
    val gitCommitsUrl: String?,

    @field:JsonProperty("blobs_url")
    val blobsUrl: String?,

    @field:JsonProperty("git_tags_url")
    val gitTagsUrl: String?,

    @field:JsonProperty("merges_url")
    val mergesUrl: String?,

    @field:JsonProperty("downloads_url")
    val downloadsUrl: String?,

    @field:JsonProperty("has_issues")
    val hasIssues: Boolean?,

    @field:JsonProperty("url")
    val url: String?,

    @field:JsonProperty("contents_url")
    val contentsUrl: String?,

    @field:JsonProperty("mirror_url")
    val mirrorUrl: Any?,

    @field:JsonProperty("milestones_url")
    val milestonesUrl: String?,

    @field:JsonProperty("teams_url")
    val teamsUrl: String?,

    @field:JsonProperty("fork")
    val fork: Boolean?,

    @field:JsonProperty("issues_url")
    val issuesUrl: String?,

    @field:JsonProperty("events_url")
    val eventsUrl: String?,

    @field:JsonProperty("issue_events_url")
    val issueEventsUrl: String?,

    @field:JsonProperty("assignees_url")
    val assigneesUrl: String?,

    @field:JsonProperty("open_issues")
    val openIssues: Int?,

    @field:JsonProperty("watchers_count")
    val watchersCount: Int?,

    @field:JsonProperty("node_id")
    val nodeId: String?,

    @field:JsonProperty("homepage")
    val homepage: Any?,

    @field:JsonProperty("forks_count")
    val forksCount: Int?,
)
