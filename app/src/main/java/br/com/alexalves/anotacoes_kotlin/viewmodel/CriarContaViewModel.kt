package br.com.alexalves.anotacoes_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CriarContaViewModel(val usuarioDAO: UsuarioDAO) : ViewModel() {

    fun criarUsuario(usuario: Usuario){
        CoroutineScope(IO).launch {
            usuarioDAO.adicionarUsuario(usuario)
        }
    }



}