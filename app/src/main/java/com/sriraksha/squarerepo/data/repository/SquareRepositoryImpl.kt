package com.sriraksha.squarerepo.data.repository

import com.sriraksha.squarerepo.data.api.SquareApi
import com.sriraksha.squarerepo.data.mapper.ApiSquareMapper
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.domain.respository.SquareRepository
import javax.inject.Inject

class SquareRepositoryImpl @Inject constructor(
    private val squareApi: SquareApi,
    private val apiSquareMapper: ApiSquareMapper
) : SquareRepository {

    override suspend fun getSquare(): List<SquareRepo> {

        return squareApi.getSquare()
            ?.mapNotNull { product ->
                apiSquareMapper.mapToDomain(product)
            }
            ?.toList()
            .orEmpty()
    }
}