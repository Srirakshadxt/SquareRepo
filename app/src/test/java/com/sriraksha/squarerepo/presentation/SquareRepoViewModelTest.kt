package com.sriraksha.squarerepo.presentation

import com.sriraksha.squarerepo.data.NoNetworkException
import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.domain.usecases.GetSquareReposUseCase
import com.sriraksha.squarerepo.presentation.utils.ResourcesProvider
import com.sriraksha.squarerepo.utils.TestDataFactory.Companion.createSquareRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SquareRepoViewModelTest {

    @MockK
    private lateinit var getSquareReposUseCase: GetSquareReposUseCase

    @MockK
    private lateinit var resourcesProvider: ResourcesProvider

    private lateinit var squareRepoViewModel: SquareRepoViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        squareRepoViewModel = SquareRepoViewModel(getSquareReposUseCase, resourcesProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadSquareRepositories should update uiState to Success`() = runTest {
        // Arrange
        val mockResponse = listOf(createSquareRepo())
        coEvery { getSquareReposUseCase.invoke() } returns Result.success(mockResponse)

        assertEquals(SquareUiState.Loading, squareRepoViewModel.uiState.value)
        // Act
        squareRepoViewModel.loadSquareRepositories()

        // Assert
        assertTrue(squareRepoViewModel.uiState.value is SquareUiState.Success)
        assertEquals(
            mockResponse,
            (squareRepoViewModel.uiState.value as SquareUiState.Success).squareRepos
        )

        assertEquals(
            1,
            (squareRepoViewModel.uiState.value as SquareUiState.Success).squareRepos.size
        )
        assertEquals(
            "Sample Repo",
            (squareRepoViewModel.uiState.value as SquareUiState.Success).squareRepos[0].name
        )
        assertEquals(
            "Sample description",
            (squareRepoViewModel.uiState.value as SquareUiState.Success).squareRepos[0].description
        )
    }

    @Test
    fun `loadSquareRepositories should handle NoNetworkException`() = runTest {
        // Arrange
        val noNetworkException = NoNetworkException("No network connection available")
        coEvery { getSquareReposUseCase() } returns Result.failure(noNetworkException)

        assertEquals(SquareUiState.Loading, squareRepoViewModel.uiState.value)
        // Act
        squareRepoViewModel.loadSquareRepositories()

        // Assert
        assertTrue(squareRepoViewModel.uiState.value is SquareUiState.Error)
        assertEquals(
            "No network connection available",
            (squareRepoViewModel.uiState.value as SquareUiState.Error).errorMessage
        )
    }

    @Test
    fun `loadSquareRepositories should handle RemoteDataSourceException`() =
        runTest {
            // Arrange
            val remoteDataSourceException = RemoteDataSourceException("Remote data source error")
            coEvery { getSquareReposUseCase() } returns Result.failure(remoteDataSourceException)

            assertEquals(SquareUiState.Loading, squareRepoViewModel.uiState.value)
            // Act
            squareRepoViewModel.loadSquareRepositories()

            // Assert
            assertEquals(
                "Remote data source error",
                (squareRepoViewModel.uiState.value as SquareUiState.Error).errorMessage
            )
        }

    @Test
    fun `loadSquareRepositories should handle unexpected exceptions`() = runTest {
        // Arrange
        val unexpectedException = RuntimeException("Unexpected error")
        coEvery { getSquareReposUseCase() } returns Result.failure(unexpectedException)

        assertEquals(SquareUiState.Loading, squareRepoViewModel.uiState.value)
        // Act
        squareRepoViewModel.loadSquareRepositories()

        // Assert
        assertEquals(
            "Unexpected error",
            (squareRepoViewModel.uiState.value as SquareUiState.Error).errorMessage
        )
    }

    @Test
    fun `loadSquareRepositories should handle empty squareResponse`() = runTest {
        // Arrange
        coEvery { getSquareReposUseCase() } returns Result.success(emptyList())
        // Act
        squareRepoViewModel.loadSquareRepositories()

        // Assert
        assertTrue(squareRepoViewModel.uiState.value is SquareUiState.Error)
        assertEquals(
            "No more pages available",
            (squareRepoViewModel.uiState.value as SquareUiState.Error).errorMessage
        )
    }
}