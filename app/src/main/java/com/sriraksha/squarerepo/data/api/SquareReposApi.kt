package com.sriraksha.squarerepo.data.api

import com.sriraksha.squarerepo.data.model.SquareRepoResponse
import retrofit2.http.GET

interface SquareReposApi {
    @GET("square/repos")
    suspend fun getSquareRepos(): SquareRepoResponse

}