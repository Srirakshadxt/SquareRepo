package com.sriraksha.squarerepo.data.repository

import com.sriraksha.squarerepo.data.NoNetworkException
import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.data.api.SquareReposApi
import com.sriraksha.squarerepo.data.mapper.ApiSquareRepoMapper
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.domain.respository.SquareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * Implements the SquareRepository interface to fetch and map square repository data,
 * handling errors and network issues.
 */
class SquareRepositoryImpl @Inject constructor(
    private val squareReposApi: SquareReposApi,
    private val apiSquareRepoMapper: ApiSquareRepoMapper
) : SquareRepository {

    override suspend fun getSquareRepo(): List<SquareRepo> =
        withContext(Dispatchers.IO) {
            try {
                val squareReposResponse = squareReposApi.getSquareRepos()
                    ?: throw RemoteDataSourceException("Square repos response is null")

                val squareReposList = squareReposResponse.mapNotNull { squareRepo ->
                    apiSquareRepoMapper.mapToDomain(squareRepo)
                }
                return@withContext squareReposList.toList()
            } catch (e: IOException) {
                throw NoNetworkException("No network connection available")
            } catch (e: Exception) {
                throw RemoteDataSourceException("Error fetching square repositories: ${e.message}")
            }
        }
}