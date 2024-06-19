package com.sriraksha.squarerepo.domain.usecase

import android.util.Log
import com.sriraksha.squarerepo.data.NoNetworkException
import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.domain.respository.SquareRepository
import com.sriraksha.squarerepo.domain.usecases.GetSquareReposUseCase
import com.sriraksha.squarerepo.utils.TestDataFactory.Companion.createSquareRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetSquareReposUseCaseTest {

    @MockK
    private lateinit var squareRepository: SquareRepository

    private lateinit var getSquareReposUseCase: GetSquareReposUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        getSquareReposUseCase = GetSquareReposUseCase(squareRepository)

        /** To solve the error:
        Method e/d in android.util.Log not mocked**/
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @Test
    fun `invoke should return list of SquareRepo on success`() = runTest {
        // Mock
        val mockResponse = listOf(createSquareRepo())
        coEvery { squareRepository.getSquareRepo() } returns mockResponse

        // Test
        val result = getSquareReposUseCase.invoke()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(mockResponse, result.getOrNull())
    }

    @Test
    fun `invoke should handle NoNetworkException`() = runTest {
        // Mock
        val noNetworkException = NoNetworkException("No network")
        coEvery { squareRepository.getSquareRepo() } throws noNetworkException

        // Test
        val result = getSquareReposUseCase.invoke()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(noNetworkException, result.exceptionOrNull())
    }

    @Test
    fun `invoke should handle RemoteDataSourceException`() = runTest {
        // Mock
        val remoteDataSourceException = RemoteDataSourceException("Remote data source error")
        coEvery { squareRepository.getSquareRepo() } throws remoteDataSourceException

        // Test
        val result = getSquareReposUseCase.invoke()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(remoteDataSourceException, result.exceptionOrNull())
    }

    @Test
    fun `invoke should handle empty squareResponse`() = runTest {
        // Mock
        val emptyList = emptyList<SquareRepo>()
        coEvery { squareRepository.getSquareRepo() } returns emptyList

        // Test
        val result = getSquareReposUseCase.invoke()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
    }
}

