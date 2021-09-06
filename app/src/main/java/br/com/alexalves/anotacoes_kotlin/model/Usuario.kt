package br.com.alexalves.anotacoes_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var nome: String,
    var senha: String,
    var cpf: String,
    var email: String,
    var dataDeNascimento: Calendar,
    var telefone: String,
)


