package br.com.alexalves.anotacoes_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alexalves.anotacoes_kotlin.database.AnotacaoDAO
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AdicionarAnotacaoViewModel(
    private val usuarioDAO: UsuarioDAO,
    private val anotacaoDAO: AnotacaoDAO
): ViewModel(){

    fun criarAnotacao(anotacao: Anotacao){
        CoroutineScope(IO).launch {
            anotacaoDAO.adicionarAnotacao(anotacao)
        }
    }
}
