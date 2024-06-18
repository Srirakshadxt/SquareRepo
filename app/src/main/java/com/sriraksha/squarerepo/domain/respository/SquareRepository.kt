package com.sriraksha.squarerepo.domain.respository

import com.sriraksha.squarerepo.data.model.SquareRepo

interface SquareRepository {

    suspend fun getSquare(): List<SquareRepo>
}