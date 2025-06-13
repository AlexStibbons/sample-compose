package com.chomp.library.domain

import com.chomp.library.core.Failure
import com.chomp.library.core.Response
import com.chomp.library.core.mapSuccessTo
import com.chomp.library.data.Faker
import com.chomp.library.data.repository.FakeRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Since it's a common use case, we leave it public and in common-domain module
 * */
class FetchDataFlowUseCase(
    private val repo: FakeRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<Faker> {
        return repo
            .fetchAsContFlow()
            .flowOn(dispatcher)
    }
}