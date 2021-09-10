package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import br.com.alexalves.anotacoes_kotlin.view.activities.AnotacoesActivity
import br.com.alexalves.anotacoes_kotlin.viewInflated.fragments.CriarContaFragment
import br.com.alexalves.anotacoes_kotlin.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var inputTextEmail: TextInputLayout
    private lateinit var inputTextSenha: TextInputLayout
    private lateinit var textEmail: String
    private lateinit var textSenha: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        configuraLoginFragment(view)

        return view
    }

    private fun configuraLoginFragment(view: View) {
        configuraLoginClickListener(view)
        configuraCriarContaClickListener(view)
        configuraCampos(view)
        configuraObserverLogin()
    }

    private fun configuraCampos(view: View) {
        inputTextEmail = view.findViewById(R.id.login_fragment_text_input_layout_email)
        inputTextSenha = view.findViewById(R.id.login_fragment_text_input_layout_senha)
        textEmail = inputTextEmail.editText?.text.toString()
        textSenha = inputTextSenha.editText?.text.toString()

        configuraFocusEmail()
        configuraFocusSenha()

    }

    private fun configuraFocusEmail() {
        inputTextEmail.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (inputTextEmail.editText?.text.toString().isBlank()) {
                    inputTextEmail.error = "Email não pode ficar vazio"
                    inputTextEmail.isErrorEnabled = true
                } else {
                    inputTextEmail.isErrorEnabled = false
                }
            }
        })
    }

    private fun configuraFocusSenha() {
        inputTextSenha.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (inputTextSenha.editText?.text.toString().isBlank()) {
                    inputTextSenha.error = "Senha não pode ficar vazia"
                    inputTextSenha.isErrorEnabled = true
                } else {
                    inputTextSenha.isErrorEnabled = false
                }
            }
        })
    }

    private fun configuraLoginClickListener(view: View) {
        val buttonLogin = view.findViewById<Button>(R.id.login_fragment_button_login)
        buttonLogin.setOnClickListener {
            configuraCampos(view)
            if (!(inputTextEmail.isErrorEnabled||inputTextSenha.isErrorEnabled)){
                loginViewModel.buscaPorEmail(textEmail)
            } else Toast.makeText(context, "Os campos devem ser corrigidos", Toast.LENGTH_LONG).show()
        }
    }

    private fun configuraObserverLogin() {
        loginViewModel.liveDataUsuarioLogado.observe(viewLifecycleOwner, Observer { usuario ->
            if(usuario != null){
                if (textSenha == usuario.senha) {
                    inputTextSenha.isErrorEnabled = false
                    fazLogin(usuario)
                } else {
                    inputTextSenha.error = "Senha ou email inválidos"
                    inputTextSenha.isErrorEnabled = true
                    Toast.makeText(context, "Senha incorreta", Toast.LENGTH_LONG).show()
                }
            } else Toast.makeText(context, "Usuario nulo", Toast.LENGTH_LONG).show()
        })
    }

    private fun fazLogin(usuario: Usuario) {
        val intent = Intent(context, AnotacoesActivity::class.java)
            .putExtra(getString(R.string.usuarioEmailArgument), usuario.email)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun configuraCriarContaClickListener(view: View) {
        val buttonCriarConta =
            view.findViewById<Button>(R.id.login_fragment_button_criar_conta)
        buttonCriarConta.setOnClickListener {
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.login_fragment_container, CriarContaFragment(), null)
                    .commit()
            }
        }

    }


}


