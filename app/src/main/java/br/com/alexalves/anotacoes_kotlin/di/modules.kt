package br.com.alexalves.anotacoes_kotlin.di

import androidx.room.Room
import br.com.alexalves.anotacoes_kotlin.database.AnotacaoDAO
import br.com.alexalves.anotacoes_kotlin.database.AppDatabase
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "anotacao_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single<UsuarioDAO>{
        get<AppDatabase>().usuarioDAO
    }

    single<AnotacaoDAO>{
        get<AppDatabase>().anotacoesDAO
    }

    viewModel {
        CriarContaViewModel(get<UsuarioDAO>())
    }
    viewModel {
        LoginViewModel(get<UsuarioDAO>())
    }
    viewModel {
        PerfilUsuarioViewModel(get<UsuarioDAO>())
    }
    viewModel {
        AdicionarAnotacaoViewModel(get<UsuarioDAO>(), get<AnotacaoDAO>())
    }

    viewModel {
        AnotacoesViewModel(get<AnotacaoDAO>())
    }


}