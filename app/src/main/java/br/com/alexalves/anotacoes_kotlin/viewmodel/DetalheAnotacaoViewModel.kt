package br.com.alexalves.anotacoes_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alexalves.anotacoes_kotlin.database.AnotacaoDAO
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalheAnotacaoViewModel(private val anotacaoDAO: AnotacaoDAO): ViewModel() {

    var anotacaoEscolhida = MutableLiveData<Anotacao>()

    fun buscaAnotacaoPorId(anotacaoId: Long) {
        CoroutineScope(IO).launch {
            val anotacaoBuscada = anotacaoDAO.buscaAnotacaoPorId(anotacaoId)
            withContext(Main){
                anotacaoEscolhida.value = anotacaoBuscada
            }
        }
    }
}
