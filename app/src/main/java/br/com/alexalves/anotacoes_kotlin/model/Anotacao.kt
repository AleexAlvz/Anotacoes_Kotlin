package br.com.alexalves.anotacoes_kotlin.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Usuario::class,
    parentColumns = arrayOf("email"),
    childColumns = arrayOf("emailUsuario"),
    onDelete = ForeignKey.CASCADE)
))

data class Anotacao(
    val emailUsuario: String,
    val titulo: String,
    val descricao: String,
    val dataCriacao: Calendar,
    val status: EnumStatus,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
