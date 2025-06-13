package com.chomp.library.data

import com.chomp.library.data.api.FakeApi
import com.chomp.library.data.api.FakeApiImpl
import com.chomp.library.data.repository.FakeRepo
import com.chomp.library.data.repository.FakeRepoImpl
import org.koin.dsl.module

val dataModule = module {
    single<FakeApi> { FakeApiImpl() }
    single<FakeRepo> { FakeRepoImpl(get()) }
}