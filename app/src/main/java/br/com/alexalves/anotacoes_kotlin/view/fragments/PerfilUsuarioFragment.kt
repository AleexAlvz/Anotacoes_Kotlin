package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import br.com.alexalves.anotacoes_kotlin.view.activities.AnotacoesActivity
import br.com.alexalves.anotacoes_kotlin.view.activities.LoginActivity
import br.com.alexalves.anotacoes_kotlin.viewmodel.LoginViewModel
import br.com.alexalves.anotacoes_kotlin.viewmodel.PerfilUsuarioViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class PerfilUsuarioFragment : Fragment() {

    private val perfilUsuarioViewModel: PerfilUsuarioViewModel by viewModel()
    private lateinit var usuarioLogado: Usuario

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false)

        buscaUsuario()
        configuraPerfil(view)
        configuraButtonLogout(view)

        return view
    }


    private fun buscaUsuario() {
        val usuarioEmail = arguments?.getString(getString(R.string.usuarioEmailArgument)).toString()
        perfilUsuarioViewModel.buscaUsuarioPorEmail(usuarioEmail)
    }

    private fun configuraPerfil(view: View) {

        val inputNome = view.findViewById<TextView>(R.id.text_input_perfil_nome)
        val inputEmail = view.findViewById<TextView>(R.id.text_input_perfil_email)
        val inputTelefone = view.findViewById<TextView>(R.id.text_input_perfil_telefone)

        perfilUsuarioViewModel.mutableLiveDataUsuario.observe(
            viewLifecycleOwner,
            Observer { usuario ->
                if (usuario != null) {
                    usuarioLogado = usuario
                    inputNome.text = usuarioLogado.nome
                    inputEmail.text = usuarioLogado.email
                    inputTelefone.text = usuarioLogado.telefone
                }
            })
    }

    private fun configuraButtonLogout(view: View) {
        val buttonLogout = view.findViewById<Button>(R.id.perfil_fragment_button_logout)
        buttonLogout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("appRun", true)
            startActivity(intent)
        }
    }
}
