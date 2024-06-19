package com.sriraksha.squarerepo.data.repository

import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.data.api.SquareReposApi
import com.sriraksha.squarerepo.data.mapper.ApiSquareRepoMapper
import com.sriraksha.squarerepo.data.model.ApiSquareRepos
import com.sriraksha.squarerepo.utils.TestDataFactory.Companion.createApiSquareReposResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SquareRepositoryImplTest {

    @MockK
    private lateinit var squareReposApi: SquareReposApi


    private lateinit var mapper: ApiSquareRepoMapper

    private lateinit var squareRepositoryImpl: SquareRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        mapper = ApiSquareRepoMapper()
        squareRepositoryImpl = SquareRepositoryImpl(squareReposApi, mapper)
    }

    @Test
    fun `given square repos api provide response then api response should be converted to  square repo`() =
        runTest {
            // Mock
            coEvery { squareReposApi.getSquareRepos() } returns createApiSquareReposResponse()

            //Test
            val result = squareRepositoryImpl.getSquareRepo()

            assertEquals(1, result.size)
            assertEquals(result.first().id, 12345)
            assertEquals(result.first().ownerId, 67890)
            assertEquals(result.first().name, "repo_name")
            assertEquals(result.first().description, "A generic description")
            assertEquals(result.first().imageUrl, "https://example.com/avatar.png")
            assertEquals(result.first().openIssuesCount, 1)
            assertEquals(result.first().createdAt, "2020-01-01T00:00:00Z")
            assertEquals(result.first().pushedAt, "2020-01-01T00:00:00Z")
            assertEquals(result.first().watchersCount, 100)
            assertEquals(result.first().organizationsUrl, "https://api.github.com/users/owner/orgs")
            assertEquals(result.first().reposUrl, "https://api.github.com/users/owner/repos")
            assertEquals(result.first().type, "Organization")
            assertEquals(result.first().siteAdmin, false)
        }

    @Test
    fun `given square repos api provide null response then api response should be converted to empty list`() =
        runTest {
            // Mock
            coEvery { squareReposApi.getSquareRepos() } returns null

            //Test
            val exception = assertThrows(RemoteDataSourceException::class.java) {
                runBlocking { squareRepositoryImpl.getSquareRepo() }
            }

            assertTrue(exception is RemoteDataSourceException)


            assertEquals(
                "Error fetching square repositories: Square repos response is null",
                exception.message
            )

        }

    @Test
    fun `given square repos api provide empty list response then api response should be converted to empty list`() =
        runTest {
            // Mock
            coEvery { squareReposApi.getSquareRepos() } returns emptyList()

            //Test
            val result = squareRepositoryImpl.getSquareRepo()

            assertEquals(emptyList<ApiSquareRepos>(), result)

        }


}