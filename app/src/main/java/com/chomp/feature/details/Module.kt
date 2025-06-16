package com.chomp.feature.details

import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun injectDetailsModule() = loadKoinModules(detailsModule)

private val detailsModule = module {
    viewModel { DetailsViewModel(get()) }
}