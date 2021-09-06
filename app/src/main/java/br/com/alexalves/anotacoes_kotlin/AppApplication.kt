package br.com.alexalves.anotacoes_kotlin

import android.app.Application
import br.com.alexalves.anotacoes_kotlin.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AppApplication)
            modules(appModules)
        }

    }
}