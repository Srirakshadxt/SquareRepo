package com.sriraksha.squarerepo.data.api

import com.sriraksha.squarerepo.data.model.SquareResponse
import retrofit2.http.GET

interface SquareApi {

    @GET("square/repos")
    suspend fun getSquare(): SquareResponse

}