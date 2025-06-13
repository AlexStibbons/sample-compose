package com.chomp.feature.homeList

import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun injectHomeModule() = loadKoinModules(homeModule)

private val homeModule = module {
    viewModel { HomeListViewModel(get()) }
}