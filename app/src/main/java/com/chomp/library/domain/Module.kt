package com.chomp.library.domain

import org.koin.dsl.module

val domainModule = module {
    factory { FetchDataResponseUseCase(get()) }
    factory { FetchDataFlowUseCase(get()) }
}