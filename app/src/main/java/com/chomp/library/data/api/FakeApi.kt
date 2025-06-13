package com.chomp.library.data.api

import com.chomp.library.data.Faker
import com.chomp.library.data.createFakeData

data class FakeApiResponse(
    val code: Int,
    val data: List<Faker>?
)

interface FakeApi {
    suspend fun fetchFakes(): FakeApiResponse
}

internal class FakeApiImpl : FakeApi {
    override suspend fun fetchFakes(): FakeApiResponse {
        return FakeApiResponse(200, createFakeData())
    }
}