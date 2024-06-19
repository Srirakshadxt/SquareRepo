package com.sriraksha.squarerepo.utils

import com.google.gson.Gson
import com.sriraksha.squarerepo.data.model.ApiSquareRepos
import com.sriraksha.squarerepo.data.model.SquareRepo

/**
 * Factory class for generating test data related to Square repositories API responses and objects.
 */
class TestDataFactory {

    companion object {

        /**
         * Creates a list of ApiSquareRepos objects from a predefined JSON response and a single object from valid JSON without optional fields.
         * Creates a SquareRepo object with customizable parameters.
         */
        fun createApiSquareReposResponse(): List<ApiSquareRepos> {
            return listOf((Gson().fromJson(apiResponse, ApiSquareRepos::class.java)))
        }

        /**
         * Creates a SquareRepo object with customizable parameters.
         */
        fun createResponseValidJsonStringWithoutOptionalFields(): ApiSquareRepos {
            return (Gson().fromJson(
                validJsonStringWithoutOptionalFields,
                ApiSquareRepos::class.java
            ))
        }

        /**
         * Creates a SquareRepo object with customizable parameters.
         */
        fun createSquareRepo(
            id: Long = 1,
            ownerId: Long = 1,
            name: String = "Sample Repo",
            description: String = "Sample description",
            imageUrl: String = "https://example.com/image.png",
            openIssuesCount: Long = 0,
            createdAt: String = "2024-01-01T12:00:00Z",
            updatedAt: String = "2024-01-01T12:00:00Z",
            pushedAt: String = "2024-01-01T12:00:00Z",
            watchersCount: Long = 0,
            organizationsUrl: String = "https://api.github.com/orgs/example",
            reposUrl: String = "https://api.github.com/repos/example/sample-repo",
            type: String = "repo",
            siteAdmin: Boolean = false
        ): SquareRepo {
            return SquareRepo(
                id, ownerId, name, description, imageUrl, openIssuesCount,
                createdAt, updatedAt, pushedAt, watchersCount, organizationsUrl,
                reposUrl, type, siteAdmin
            )
        }

        private val apiResponse = """
        {
            "id": 12345,
            "node_id": "NODE_ID",
            "name": "repo_name",
            "full_name": "owner/repo_name",
            "private": false,
            "owner": {
                "login": "owner_login",
                "id": 67890,
                "node_id": "OWNER_NODE_ID",
                "avatar_url": "https://example.com/avatar.png",
                "gravatar_id": "",
                "url": "https://api.github.com/users/owner",
                "html_url": "https://github.com/owner",
                "followers_url": "https://api.github.com/users/owner/followers",
                "following_url": "https://api.github.com/users/owner/following{/other_user}",
                "gists_url": "https://api.github.com/users/owner/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/owner/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/owner/subscriptions",
                "organizations_url": "https://api.github.com/users/owner/orgs",
                "repos_url": "https://api.github.com/users/owner/repos",
                "events_url": "https://api.github.com/users/owner/events{/privacy}",
                "received_events_url": "https://api.github.com/users/owner/received_events",
                "type": "Organization",
                "site_admin": false
            },
            "html_url": "https://github.com/owner/repo_name",
            "description": "A generic description",
            "fork": false,
            "url": "https://api.github.com/repos/owner/repo_name",
            "forks_url": "https://api.github.com/repos/owner/repo_name/forks",
            "keys_url": "https://api.github.com/repos/owner/repo_name/keys{/key_id}",
            "collaborators_url": "https://api.github.com/repos/owner/repo_name/collaborators{/collaborator}",
            "teams_url": "https://api.github.com/repos/owner/repo_name/teams",
            "hooks_url": "https://api.github.com/repos/owner/repo_name/hooks",
            "issue_events_url": "https://api.github.com/repos/owner/repo_name/issues/events{/number}",
            "events_url": "https://api.github.com/repos/owner/repo_name/events",
            "assignees_url": "https://api.github.com/repos/owner/repo_name/assignees{/user}",
            "branches_url": "https://api.github.com/repos/owner/repo_name/branches{/branch}",
            "tags_url": "https://api.github.com/repos/owner/repo_name/tags",
            "blobs_url": "https://api.github.com/repos/owner/repo_name/git/blobs{/sha}",
            "git_tags_url": "https://api.github.com/repos/owner/repo_name/git/tags{/sha}",
            "git_refs_url": "https://api.github.com/repos/owner/repo_name/git/refs{/sha}",
            "trees_url": "https://api.github.com/repos/owner/repo_name/git/trees{/sha}",
            "statuses_url": "https://api.github.com/repos/owner/repo_name/statuses/{sha}",
            "languages_url": "https://api.github.com/repos/owner/repo_name/languages",
            "stargazers_url": "https://api.github.com/repos/owner/repo_name/stargazers",
            "contributors_url": "https://api.github.com/repos/owner/repo_name/contributors",
            "subscribers_url": "https://api.github.com/repos/owner/repo_name/subscribers",
            "subscription_url": "https://api.github.com/repos/owner/repo_name/subscription",
            "commits_url": "https://api.github.com/repos/owner/repo_name/commits{/sha}",
            "git_commits_url": "https://api.github.com/repos/owner/repo_name/git/commits{/sha}",
            "comments_url": "https://api.github.com/repos/owner/repo_name/comments{/number}",
            "issue_comment_url": "https://api.github.com/repos/owner/repo_name/issues/comments{/number}",
            "contents_url": "https://api.github.com/repos/owner/repo_name/contents/{+path}",
            "compare_url": "https://api.github.com/repos/owner/repo_name/compare/{base}...{head}",
            "merges_url": "https://api.github.com/repos/owner/repo_name/merges",
            "archive_url": "https://api.github.com/repos/owner/repo_name/{archive_format}{/ref}",
            "downloads_url": "https://api.github.com/repos/owner/repo_name/downloads",
            "issues_url": "https://api.github.com/repos/owner/repo_name/issues{/number}",
            "pulls_url": "https://api.github.com/repos/owner/repo_name/pulls{/number}",
            "milestones_url": "https://api.github.com/repos/owner/repo_name/milestones{/number}",
            "notifications_url": "https://api.github.com/repos/owner/repo_name/notifications{?since,all,participating}",
            "labels_url": "https://api.github.com/repos/owner/repo_name/labels{/name}",
            "releases_url": "https://api.github.com/repos/owner/repo_name/releases{/id}",
            "deployments_url": "https://api.github.com/repos/owner/repo_name/deployments",
            "created_at": "2020-01-01T00:00:00Z",
            "updated_at": "2020-01-01T00:00:00Z",
            "pushed_at": "2020-01-01T00:00:00Z",
            "git_url": "git://github.com/owner/repo_name.git",
            "ssh_url": "git@github.com:owner/repo_name.git",
            "clone_url": "https://github.com/owner/repo_name.git",
            "svn_url": "https://github.com/owner/repo_name",
            "homepage": "https://example.com",
            "size": 1000,
            "stargazers_count": 100,
            "watchers_count": 100,
            "language": "Kotlin",
            "has_issues": true,
            "has_projects": true,
            "has_downloads": true,
            "has_wiki": true,
            "has_pages": true,
            "has_discussions": true,
            "forks_count": 10,
            "mirror_url": null,
            "archived": false,
            "disabled": false,
            "open_issues_count": 1,
            "license": {
                "key": "mit",
                "name": "MIT License",
                "spdx_id": "MIT",
                "url": "https://api.github.com/licenses/mit",
                "node_id": "MDc6TGljZW5zZTEz"
            },
            "allow_forking": true,
            "is_template": false,
            "web_commit_signoff_required": false,
            "topics": [
                "topic1",
                "topic2"
            ],
            "visibility": "public",
            "forks": 10,
            "open_issues": 1,
            "watchers": 100,
            "default_branch": "main",
            "permissions": {
                "admin": false,
                "maintain": false,
                "push": false,
                "triage": false,
                "pull": true
            }
        }
    """.trimIndent()

        private val validJsonStringWithoutOptionalFields = """
              {
                  "id": 1,
                  "node_id": "MDEwOlJlcG9zaXRvcnkx",
                  "name": "repo-name",
                  "owner": {
                      "id": 1,
                      "avatar_url": "https://avatars.githubusercontent.com/u/1?v=4",
                      "organizations_url": "https://api.github.com/users/owner/orgs",
                      "repos_url": "https://api.github.com/users/owner/repos",
                      "type": "User",
                      "site_admin": false
                  },
                  "open_issues_count": 3,
                  "created_at": "2023-01-01T00:00:00Z",
                  "updated_at": "2023-01-02T00:00:00Z",
                  "pushed_at": "2023-01-03T00:00:00Z",
                  "watchers_count": 78
              }
              """.trimIndent()
    }
}

