package br.com.alexalves.anotacoes_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilUsuarioViewModel(private val usuarioDAO: UsuarioDAO) : ViewModel(){

    val mutableLiveDataUsuario = MutableLiveData<Usuario>()

    fun buscaUsuarioPorEmail(usuarioEmail: String) {
        CoroutineScope(IO).launch {
            val usuarioBuscado = usuarioDAO.buscaUsuarioPorEmail(usuarioEmail)
            withContext(Main){
                mutableLiveDataUsuario.value = usuarioBuscado
            }
        }

    }

}
