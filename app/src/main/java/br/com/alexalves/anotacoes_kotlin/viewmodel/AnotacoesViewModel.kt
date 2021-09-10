package br.com.alexalves.anotacoes_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alexalves.anotacoes_kotlin.database.AnotacaoDAO
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnotacoesViewModel(private val anotacaoDAO: AnotacaoDAO): ViewModel() {

    val anotacoesPorUsuario = MutableLiveData<List<Anotacao>>()

    fun buscaAnotacoesPorUsuario(usuarioEmail: String) {
        CoroutineScope(IO).launch {
            val anotacoes = anotacaoDAO.buscaAnotacoesPorEmail(usuarioEmail)
            withContext(Main){
                anotacoesPorUsuario.value = anotacoes
            }
        }
    }

}
