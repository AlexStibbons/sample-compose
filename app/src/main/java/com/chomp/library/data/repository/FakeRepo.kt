package com.chomp.library.data.repository

import com.chomp.library.core.Failure
import com.chomp.library.core.Response
import com.chomp.library.data.FAKE_DATA
import com.chomp.library.data.Faker
import com.chomp.library.data.api.FakeApi
import com.chomp.library.data.createFakeData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface FakeRepo {
    suspend fun fetchDataAsResponse(): Response<Failure, List<Faker>>
    suspend fun fetchAsContFlow(): Flow<Faker>
}

internal class FakeRepoImpl(
    private val fakeApi: FakeApi
) : FakeRepo {

    override suspend fun fetchDataAsResponse(): Response<Failure, List<Faker>> {
        // fetch from api
        // map api response to repository response
        return Response.success(createFakeData())
    }

    override suspend fun fetchAsContFlow(): Flow<Faker> = flow {
        FAKE_DATA.forEach {
            delay(2000L)
            emit(it)
        }
    }

}