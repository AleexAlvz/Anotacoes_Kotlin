package br.com.alexalves.anotacoes_kotlin.database

import androidx.room.*
import br.com.alexalves.anotacoes_kotlin.model.Anotacao

@Dao
abstract class AnotacoesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun adicionarAnotacao(anotacao: Anotacao)

    @Delete
    abstract fun deletarAnotacao(anotacao: Anotacao)

    @Query("SELECT * FROM Anotacao")
    abstract fun buscarAnotacoes()

    @Query("SELECT * FROM Anotacao WHERE Anotacao.idUsuario = :idUsuario")
    abstract fun buscaAnotacaoPorId(idUsuario: Long)

}
