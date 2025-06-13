package com.chomp.feature.login

import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun injectLoginModule() = loadKoinModules(loginModule)

private val loginModule = module {
    viewModel { LoginViewModel() }
}