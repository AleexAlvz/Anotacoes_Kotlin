package br.com.alexalves.anotacoes_kotlin.di

import androidx.room.Room
import br.com.alexalves.anotacoes_kotlin.database.AppDatabase
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.viewmodel.CriarContaViewModel
import org.koin.dsl.module

val appModules = module {

    single<CriarContaViewModel> {
        CriarContaViewModel(get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "AnotacoesDB"
        ).build()
    }

}