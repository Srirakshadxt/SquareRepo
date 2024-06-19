package com.sriraksha.squarerepo.data.mapper

import com.sriraksha.squarerepo.utils.TestDataFactory.Companion.createApiSquareReposResponse
import com.sriraksha.squarerepo.utils.TestDataFactory.Companion.createResponseValidJsonStringWithoutOptionalFields
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ApiSquareRepoMapperTest {

    private lateinit var apiSquareRepoMapper: ApiSquareRepoMapper;

    @Before
    fun setUp() {
        apiSquareRepoMapper = ApiSquareRepoMapper()
    }

    @Test
    fun `test mapToDomain with valid input`() {
        val squareRepo = apiSquareRepoMapper.mapToDomain(createApiSquareReposResponse().first())

        assertNotNull(squareRepo)
        assertEquals(12345L, squareRepo?.id)
        assertEquals(67890L, squareRepo?.ownerId)
        assertEquals("repo_name", squareRepo?.name)
        assertEquals("A generic description", squareRepo?.description)
        assertEquals("https://example.com/avatar.png", squareRepo?.imageUrl)
        assertEquals(1L, squareRepo?.openIssuesCount)
        assertEquals("2020-01-01T00:00:00Z", squareRepo?.createdAt)
        assertEquals("2020-01-01T00:00:00Z", squareRepo?.updatedAt)
        assertEquals("2020-01-01T00:00:00Z", squareRepo?.pushedAt)
        assertEquals(100L, squareRepo?.watchersCount)
        assertEquals("https://api.github.com/users/owner/orgs", squareRepo?.organizationsUrl)
        assertEquals("https://api.github.com/users/owner/repos", squareRepo?.reposUrl)
        assertEquals("Organization", squareRepo?.type)
        assertEquals(false, squareRepo?.siteAdmin)
    }

    @Test
    fun `test mapToDomain with empty name`() {
        val squareRepo = apiSquareRepoMapper.mapToDomain(
            createApiSquareReposResponse().first().copy(name = "")
        )
        assertNull(squareRepo)
    }

    @Test
    fun `test mapToDomain with missing optional fields`() {
        val squareRepo =
            apiSquareRepoMapper.mapToDomain(createResponseValidJsonStringWithoutOptionalFields())

        assertNotNull(squareRepo)
        assertEquals(1L, squareRepo?.id)
        assertEquals(1L, squareRepo?.ownerId)
        assertEquals("repo-name", squareRepo?.name)
        assertEquals("", squareRepo?.description) // Default value for missing description
        assertEquals("https://avatars.githubusercontent.com/u/1?v=4", squareRepo?.imageUrl)
        assertEquals(3L, squareRepo?.openIssuesCount)
        assertEquals("2023-01-01T00:00:00Z", squareRepo?.createdAt)
        assertEquals("2023-01-02T00:00:00Z", squareRepo?.updatedAt)
        assertEquals("2023-01-03T00:00:00Z", squareRepo?.pushedAt)
        assertEquals(78L, squareRepo?.watchersCount)
        assertEquals("https://api.github.com/users/owner/orgs", squareRepo?.organizationsUrl)
        assertEquals("https://api.github.com/users/owner/repos", squareRepo?.reposUrl)
        assertEquals("User", squareRepo?.type)
        assertEquals(false, squareRepo?.siteAdmin)
    }

}