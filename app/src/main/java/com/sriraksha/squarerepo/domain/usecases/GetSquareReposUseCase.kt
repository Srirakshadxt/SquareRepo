package com.sriraksha.squarerepo.domain.usecases

import android.util.Log
import com.sriraksha.squarerepo.data.NoNetworkException
import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.domain.respository.SquareRepository
import javax.inject.Inject

class GetSquareReposUseCase @Inject constructor(
    private val squareRepository: SquareRepository
) {
    companion object {
        private const val TAG = "GetSquareRepositories"
    }

    suspend operator fun invoke(): Result<List<SquareRepo>> {
        return try {
            Log.i(TAG, "Getting square repositories")
            val squareResponse = squareRepository.getSquareRepo()
            Log.d(TAG, "Got repositories successfully: $squareRepository")
            Result.success(squareResponse)
        } catch (e: NoNetworkException) {
            Result.failure(e)
        } catch (e: RemoteDataSourceException) {
            Log.e(TAG, "Something went wrong while getting dates.", e)
            Result.failure(e)
        }
    }
}