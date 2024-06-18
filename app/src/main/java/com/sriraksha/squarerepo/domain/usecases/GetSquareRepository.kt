package com.sriraksha.squarerepo.domain.usecases

import android.util.Log
import com.sriraksha.squarerepo.data.NoNetworkException
import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.domain.respository.SquareRepository
import javax.inject.Inject


class GetSquareRepository @Inject constructor(
    private val squareRepository: SquareRepository
) {
    companion object {
        private const val TAG = "GetSquareRepositories"
    }

    suspend operator fun invoke(): Result<List<SquareRepo>> {
        return try {
            Log.i(TAG, "Getting square repositories")
            val squareRepository = squareRepository.getSquare()
            Log.d(TAG, "Got repositories successfully: $squareRepository")
            Result.success(squareRepository)
        } catch (e: NoNetworkException) {
            Log.e(TAG, "Something went wrong while getting dates.", e)
            Result.failure(e)
        } catch (e: RemoteDataSourceException) {
            Result.failure(e)
        }
    }
}