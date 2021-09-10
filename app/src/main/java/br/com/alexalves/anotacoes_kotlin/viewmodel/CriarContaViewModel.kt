package br.com.alexalves.anotacoes_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CriarContaViewModel(private val usuarioDAO: UsuarioDAO) : ViewModel() {

    var emailUsuarioRecentementeCriado = MutableLiveData<String>()

    fun criarUsuario(usuario: Usuario){
        CoroutineScope(IO).launch {
            try {
                usuarioDAO.adicionarUsuario(usuario)
                withContext(Main){
                    emailUsuarioRecentementeCriado.value = usuario.email
                }
            } catch (e: Exception){
                Log.e("erro", e.message.toString())
                withContext(Main){
                    emailUsuarioRecentementeCriado.value = "erro"
                }
            }
        }
    }



}