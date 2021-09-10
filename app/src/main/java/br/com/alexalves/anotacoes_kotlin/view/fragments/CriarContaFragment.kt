package br.com.alexalves.anotacoes_kotlin.viewInflated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Usuario
import br.com.alexalves.anotacoes_kotlin.view.fragments.LoginFragment
import br.com.alexalves.anotacoes_kotlin.viewmodel.CriarContaViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class CriarContaFragment : Fragment() {

    private lateinit var viewInflated: View
    private lateinit var emailNovoUsuario: String
    private val criarContaViewModel: CriarContaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewInflated = inflater.inflate(R.layout.fragment_criar_conta, container, false)

        verificaNome()
        verificaSenha()
        verificaConfirmarSenha()
        configuraObserverNovaConta()
        configuraButtonCriarConta()

        return viewInflated
    }

    private fun configuraObserverNovaConta() {
        criarContaViewModel.emailUsuarioRecentementeCriado.observe(viewLifecycleOwner, Observer { email ->
            if (email.equals(emailNovoUsuario)){
                activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .replace(R.id.login_fragment_container, LoginFragment(), null)
                        .commit()
                }
            } else if(email.equals("erro")){
                Toast.makeText(context, "Já existe uma conta com esse email", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configuraButtonCriarConta() {
        val buttonCriarConta =
            viewInflated.findViewById<Button>(R.id.button_criar_conta_fragment_criar_conta)
        buttonCriarConta.setOnClickListener {
            if (!verificaErrosNovoUsuario()) {
                buscaUsuario() { usuario ->
                    run {
                        criarContaViewModel.criarUsuario(usuario)
                    }
                }
            }
        }

    }

    private fun buscaUsuario(usuarioRetornado: (usuario: Usuario) -> Unit) {
        val nome =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_nome).editText?.text.toString()
        val senha =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha).editText?.text.toString()
        emailNovoUsuario =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_email).editText?.text.toString()
        val telefone =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_telefone).editText?.text.toString()
        val usuario = Usuario(nome, senha, emailNovoUsuario, telefone)
        usuarioRetornado(usuario)
    }

    private fun verificaErrosNovoUsuario(): Boolean {
        val errorNome =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_nome).isErrorEnabled
        val errorSenha =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha).isErrorEnabled
        val errorConfirmarSenha =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_confirmar_senha).isErrorEnabled
        val errorEmail =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_email).isErrorEnabled
        val errorTelefone =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_telefone).isErrorEnabled
        return (errorNome || errorSenha || errorConfirmarSenha || errorEmail || errorTelefone)
    }

    private fun verificaNome() {
        val textInputNome =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_nome)
        textInputNome.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeNome(
                hasFocus,
                textInputNome
            )
        }
    }

    private fun verificaSenha() {
        val textInputSenha =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha)
        textInputSenha.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeSenha(
                hasFocus,
                textInputSenha
            )
        }
    }

    private fun verificaConfirmarSenha() {
        val textInputSenha =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha)
        val textInputConfirmarSenha =
            viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_confirmar_senha)
        textInputConfirmarSenha.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeConfirmarSenha(
                hasFocus,
                textInputConfirmarSenha,
                textInputSenha
            )
        }
    }

    private fun onFocusChangeConfirmarSenha(
        hasFocus: Boolean,
        textInputConfirmarSenha: TextInputLayout,
        textInputSenha: TextInputLayout
    ) {
        val textoConfirmarSenha = textInputConfirmarSenha.editText?.text.toString()
        val textoSenha = textInputSenha.editText?.text.toString()

        if (!hasFocus) {
            if (textoConfirmarSenha.length == 0) {
                textInputConfirmarSenha.error = "A confirmação da senha não pode estar vazia"
                textInputConfirmarSenha.isErrorEnabled = true
            } else if (!textoConfirmarSenha.equals(textoSenha)) {
                textInputConfirmarSenha.error = "A confirmação da senha difere do campo anterior"
                textInputConfirmarSenha.isErrorEnabled = true
            }
        } else {
            textInputConfirmarSenha.isErrorEnabled = false
        }
    }

    private fun onFocusChangeNome(
        hasFocus: Boolean,
        textInputNome: TextInputLayout
    ) {
        val texto = textInputNome.editText?.text.toString()
        if (!hasFocus) {
            if (texto.isNullOrBlank()) {
                textInputNome.error = "O nome não pode estar vazio"
                textInputNome.isErrorEnabled = true
            }
        } else {
            textInputNome.isErrorEnabled = false
        }
    }

    private fun onFocusChangeSenha(
        hasFocus: Boolean,
        textInput: TextInputLayout
    ) {
        val texto = textInput.editText?.text.toString()
        if (!hasFocus) {
            if (texto.length == 0) {
                textInput.error = "A senha não pode estar vazia"
                textInput.isErrorEnabled = true
            } else if (texto.length < 5) {
                textInput.error = "Senha deve ter pelo menos 5 algorismos"
                textInput.isErrorEnabled = true
            } else if (texto.length > 20) {
                textInput.error = "Senha deve ter no máximo 20 algorismos"
                textInput.isErrorEnabled = true
            } else {
                textInput.isErrorEnabled = false
            }
        }
    }
}
