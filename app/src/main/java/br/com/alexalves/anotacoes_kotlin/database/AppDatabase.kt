package br.com.alexalves.anotacoes_kotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.Usuario

@Database(entities = [Anotacao::class, Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val anotacoesDAO : AnotacoesDAO
    abstract val usuarioDAO : UsuarioDAO

}
