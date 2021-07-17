/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RepoCreateResponse.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.editor.git.model

import com.google.gson.annotations.SerializedName

data class RepoCreateResponse(
    @SerializedName("stargazers_count")
    val stargazersCount: Int,

    @SerializedName("pushed_at")
    val pushedAt: String,

    @SerializedName("subscription_url")
    val subscriptionUrl: String,

    @SerializedName("language")
    val language: Any,

    @SerializedName("branches_url")
    val branchesUrl: String,

    @SerializedName("issue_comment_url")
    val issueCommentUrl: String,

    @SerializedName("allow_rebase_merge")
    val allowRebaseMerge: Boolean,

    @SerializedName("labels_url")
    val labelsUrl: String,

    @SerializedName("subscribers_url")
    val subscribersUrl: String,

    @SerializedName("permissions")
    val permissions: Permissions,

    @SerializedName("releases_url")
    val releasesUrl: String,

    @SerializedName("svn_url")
    val svnUrl: String,

    @SerializedName("subscribers_count")
    val subscribersCount: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("forks")
    val forks: Int,

    @SerializedName("archive_url")
    val archiveUrl: String,

    @SerializedName("allow_merge_commit")
    val allowMergeCommit: Boolean,

    @SerializedName("git_refs_url")
    val gitRefsUrl: String,

    @SerializedName("forks_url")
    val forksUrl: String,

    @SerializedName("statuses_url")
    val statusesUrl: String,

    @SerializedName("network_count")
    val networkCount: Int,

    @SerializedName("ssh_url")
    val sshUrl: String,

    @SerializedName("license")
    val license: Any,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("size")
    val size: Int,

    @SerializedName("languages_url")
    val languagesUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("collaborators_url")
    val collaboratorsUrl: String,

    @SerializedName("clone_url")
    val cloneUrl: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("pulls_url")
    val pullsUrl: String,

    @SerializedName("default_branch")
    val defaultBranch: String,

    @SerializedName("hooks_url")
    val hooksUrl: String,

    @SerializedName("trees_url")
    val treesUrl: String,

    @SerializedName("tags_url")
    val tagsUrl: String,

    @SerializedName("private")
    val jsonMemberPrivate: Boolean,

    @SerializedName("contributors_url")
    val contributorsUrl: String,

    @SerializedName("has_downloads")
    val hasDownloads: Boolean,

    @SerializedName("notifications_url")
    val notificationsUrl: String,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("watchers")
    val watchers: Int,

    @SerializedName("keys_url")
    val keysUrl: String,

    @SerializedName("deployments_url")
    val deploymentsUrl: String,

    @SerializedName("has_projects")
    val hasProjects: Boolean,

    @SerializedName("archived")
    val archived: Boolean,

    @SerializedName("has_wiki")
    val hasWiki: Boolean,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("comments_url")
    val commentsUrl: String,

    @SerializedName("stargazers_url")
    val stargazersUrl: String,

    @SerializedName("disabled")
    val disabled: Boolean,

    @SerializedName("delete_branch_on_merge")
    val deleteBranchOnMerge: Boolean,

    @SerializedName("git_url")
    val gitUrl: String,

    @SerializedName("has_pages")
    val hasPages: Boolean,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("allow_squash_merge")
    val allowSquashMerge: Boolean,

    @SerializedName("commits_url")
    val commitsUrl: String,

    @SerializedName("compare_url")
    val compareUrl: String,

    @SerializedName("git_commits_url")
    val gitCommitsUrl: String,

    @SerializedName("blobs_url")
    val blobsUrl: String,

    @SerializedName("git_tags_url")
    val gitTagsUrl: String,

    @SerializedName("merges_url")
    val mergesUrl: String,

    @SerializedName("downloads_url")
    val downloadsUrl: String,

    @SerializedName("has_issues")
    val hasIssues: Boolean,

    @SerializedName("url")
    val url: String,

    @SerializedName("contents_url")
    val contentsUrl: String,

    @SerializedName("mirror_url")
    val mirrorUrl: Any,

    @SerializedName("milestones_url")
    val milestonesUrl: String,

    @SerializedName("teams_url")
    val teamsUrl: String,

    @SerializedName("fork")
    val fork: Boolean,

    @SerializedName("issues_url")
    val issuesUrl: String,

    @SerializedName("events_url")
    val eventsUrl: String,

    @SerializedName("issue_events_url")
    val issueEventsUrl: String,

    @SerializedName("assignees_url")
    val assigneesUrl: String,

    @SerializedName("open_issues")
    val openIssues: Int,

    @SerializedName("watchers_count")
    val watchersCount: Int,

    @SerializedName("node_id")
    val nodeId: String,

    @SerializedName("homepage")
    val homepage: Any,

    @SerializedName("forks_count")
    val forksCount: Int
) : GitResultWrapper
