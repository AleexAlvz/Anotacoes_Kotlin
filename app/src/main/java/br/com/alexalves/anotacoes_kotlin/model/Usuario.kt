package br.com.alexalves.anotacoes_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    var nome: String,
    var senha: String,
    @PrimaryKey()
    var email: String,
    var telefone: String,
)


