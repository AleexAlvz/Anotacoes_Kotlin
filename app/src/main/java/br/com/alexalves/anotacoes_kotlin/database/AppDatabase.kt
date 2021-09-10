package br.com.alexalves.anotacoes_kotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.Usuario

@Database(entities = [Anotacao::class, Usuario::class], version = 3, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val anotacoesDAO : AnotacaoDAO
    abstract val usuarioDAO : UsuarioDAO

}
