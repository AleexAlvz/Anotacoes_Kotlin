package br.com.alexalves.anotacoes_kotlin.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Usuario::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("idUsuario"),
    onDelete = ForeignKey.CASCADE)
))

data class Anotacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idUsuario: Long,
    val titulo: String,
    val descricao: String,
    val dataCriacao: Calendar,
    val status: EnumStatus
)
