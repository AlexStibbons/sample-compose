package com.chomp

import android.app.Application
import com.chomp.library.data.dataModule
import com.chomp.library.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChompApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChompApplication)
            modules(
                dataModule,
                domainModule
            )
        }
    }
}