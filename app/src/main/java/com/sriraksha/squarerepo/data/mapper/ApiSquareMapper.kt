package com.sriraksha.squarerepo.data.mapper

import com.sriraksha.squarerepo.data.model.ApiSquare
import com.sriraksha.squarerepo.data.model.SquareRepo
import javax.inject.Inject

class ApiSquareMapper @Inject constructor() :
    ApiMapper<ApiSquare, SquareRepo?> {
    override fun mapToDomain(input: ApiSquare): SquareRepo? {
        // Validate id
        val id = input.id ?: return null

        // Validate Owner Id
        val ownerId = input.owner.id ?: return null

        if (input.name.isEmpty()) {
            return null
        }

        return SquareRepo(
            id = id,
            ownerId = ownerId,
            name = input.name,
            description = input.description ?: "",
            imageUrl = input.owner.avatarUrl,
            openIssuesCount = input.openIssuesCount,
            createdAt = input.createdAt,
            updatedAt = input.updatedAt,
            pushedAt = input.pushedAt,
            watchersCount = input.watchersCount,
            organizationsUrl = input.owner.organizationsUrl,
            reposUrl = input.owner.reposUrl,
            type = input.owner.type,
            siteAdmin = input.owner.siteAdmin,

            )
    }
}



