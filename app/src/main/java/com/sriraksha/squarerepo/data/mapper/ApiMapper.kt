package com.sriraksha.squarerepo.data.mapper

interface ApiMapper<I, O> {
    /**
     * @param input represents the API model
     * @return the Domain model
     */
    fun mapToDomain(input: I): O
}
