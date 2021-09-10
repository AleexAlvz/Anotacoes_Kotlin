package br.com.alexalves.anotacoes_kotlin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alexalves.anotacoes_kotlin.model.Usuario

@Dao
abstract class UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract fun adicionarUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE email = :email")
    abstract fun buscaUsuarioPorEmail(email: String): Usuario


}
