package br.com.alexalves.anotacoes_kotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.alexalves.anotacoes_kotlin.model.Anotacao

@Dao
abstract class AnotacaoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun adicionarAnotacao(anotacao: Anotacao)

    @Delete
    abstract fun deletarAnotacao(anotacao: Anotacao)

    @Query("SELECT * FROM Anotacao WHERE Anotacao.id = :idAnotacao")
    abstract fun buscaAnotacaoPorId(idAnotacao: Long): Anotacao

    @Query("SELECT * FROM Anotacao WHERE emailUsuario = :email")
    abstract fun buscaAnotacoesPorEmail(email: String): List<Anotacao>

}
