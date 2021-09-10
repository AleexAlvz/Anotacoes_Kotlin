package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
    private lateinit var inflatedView: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflatedView = inflater.inflate(R.layout.fragment_login, container, false)
        configuraLoginFragment()
        return inflatedView
    }

    private fun configuraLoginFragment() {
        configuraLoginClickListener()
        configuraCriarContaClickListener()
        configuraObserverLogin()
    }

    private fun configuraLoginClickListener() {
        val buttonLogin = inflatedView.findViewById<Button>(R.id.login_fragment_button_login)
        buttonLogin.setOnClickListener {

            inputTextEmail = inflatedView.findViewById<TextInputLayout>(R.id.login_fragment_text_input_layout_email)
            inputTextSenha = inflatedView.findViewById<TextInputLayout>(R.id.login_fragment_text_input_layout_senha)
            val textoEmail = inputTextEmail.editText?.text.toString()
            val textoSenha = inputTextSenha.editText?.text.toString()

            if (!(textoEmail.isBlank()||textoSenha.isBlank())){
                loginViewModel.buscaPorEmail(textoEmail)
            } else {
                configuraAlerta(inflatedView, "Os campos devem ser preenchidos")
            }
        }
    }

    private fun configuraObserverLogin() {
        loginViewModel.liveDataUsuarioLogado.observe(viewLifecycleOwner, Observer { usuario ->
            if(usuario != null) {
                val textoSenha = inputTextSenha.editText?.text.toString()
                if (textoSenha == usuario.senha) {
                    fazLogin(usuario)
                } else {
                    configuraAlerta(inflatedView, "A senha está incorreta")
                }
            } else {
                configuraAlerta(inflatedView, "Email não encontrado")
            }
        })
    }

    private fun configuraAlerta(view: View, alerta: String) {
        val textAlerta = view.findViewById<TextView>(R.id.login_fragment_text_alerta_erro_login)
        textAlerta.setText(alerta)
        textAlerta.visibility = TextView.VISIBLE
    }

    private fun fazLogin(usuario: Usuario) {
        val intent = Intent(context, AnotacoesActivity::class.java)
            .putExtra(getString(R.string.usuarioEmailArgument), usuario.email)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun configuraCriarContaClickListener() {
        val buttonCriarConta =
            inflatedView.findViewById<Button>(R.id.login_fragment_button_criar_conta)
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


