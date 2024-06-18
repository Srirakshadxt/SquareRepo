package com.sriraksha.squarerepo.data.model

data class SquareRepo(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val openIssuesCount: Long,
    val createdAt: String,
    val updatedAt: String,
    val pushedAt: String,
    val watchersCount: Long,
    val organizationsUrl: String,
    val reposUrl: String,
    val type: String,
    val siteAdmin: Boolean,
)