package com.sriraksha.squarerepo.data.api

import com.sriraksha.squarerepo.data.model.SquareRepoResponse
import retrofit2.http.GET

/**
 * Fetches a list of square repositories from the API endpoint `/square/repos`.
 */
interface SquareReposApi {
    @GET("square/repos")
    suspend fun getSquareRepos(): SquareRepoResponse

}