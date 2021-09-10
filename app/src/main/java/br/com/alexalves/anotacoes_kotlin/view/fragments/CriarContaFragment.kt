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
            if (validaNovoUsuario()) {
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

        //Formata telefone para salvar
        val telefoneFormatado = "(${telefone.subSequence(0,2)}) ${telefone.subSequence(2,7)}-${telefone.subSequence(7,11)}"

        val usuario = Usuario(nome, senha, emailNovoUsuario, telefoneFormatado)
        usuarioRetornado(usuario)
    }

    private fun validaNovoUsuario(): Boolean {
        validaCamposNovoUsuario()
        val temErros = verificaErrosNovoUsuario()
        return !temErros
    }

    private fun verificaErrosNovoUsuario(): Boolean {
        val errorNome = temErro(R.id.criar_conta_fragment_input_nome)
        val errorSenha = temErro(R.id.criar_conta_fragment_input_senha)
        val errorConfirmarSenha = temErro(R.id.criar_conta_fragment_input_confirmar_senha)
        val errorEmail = temErro(R.id.criar_conta_fragment_input_email)
        val errorTelefone = temErro(R.id.criar_conta_fragment_input_telefone)
        return (errorNome || errorSenha || errorConfirmarSenha || errorEmail || errorTelefone)
    }

    private fun validaCamposNovoUsuario() {
        validaNome()
        validaSenha()
        validaConfirmarSenha()
        validaEmail()
        validaTelefone()
    }

    private fun validaNome() {
        val inputNome = viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_nome)
        val textoNome = inputNome.editText?.text.toString()
        if (textoNome.isBlank()){
            inputNome.error = "Campo obrigatório"
            inputNome.isErrorEnabled = true
        } else if (textoNome.length<3){
            inputNome.error = "O nome deve ter pelo menos 3 caracteres"
            inputNome.isErrorEnabled = true
        } else if (textoNome.length>15){
            inputNome.error = "O nome deve ter no máximo 15 caracteres"
            inputNome.isErrorEnabled = true
        } else inputNome.isErrorEnabled = false
    }

    private fun validaSenha() {
        val inputSenha = viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha)
        val textoSenha = inputSenha.editText?.text.toString()

        if (textoSenha.isBlank()){
            inputSenha.error = "Campo obrigatório"
            inputSenha.isErrorEnabled = true
        } else if (textoSenha.length<5){
            inputSenha.error = "A senha deve ter pelo menos 5 caracteres"
            inputSenha.isErrorEnabled = true
        } else if (textoSenha.length>20){
            inputSenha.error = "A senha deve ter no máximo 20 caracteres"
            inputSenha.isErrorEnabled = true
        } else inputSenha.isErrorEnabled = false
    }

    private fun validaConfirmarSenha() {
        val inputConfirmarSenha = viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_confirmar_senha)
        val textoConfirmarSenha = inputConfirmarSenha.editText?.text.toString()
        val textoSenha = viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha).editText?.text.toString()

        if (textoConfirmarSenha.isBlank()){
            inputConfirmarSenha.error = "Campo obrigatório"
            inputConfirmarSenha.isErrorEnabled = true
        } else if (!textoConfirmarSenha.equals(textoSenha)){
            inputConfirmarSenha.error = "A confirmação da senha deve ser a mesma que a senha"
            inputConfirmarSenha.isErrorEnabled = true
        } else inputConfirmarSenha.isErrorEnabled = false
    }

    private fun validaEmail() {
        val inputEmail = viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_email)
        val textoEmail = inputEmail.editText?.text.toString()

        if (textoEmail.isBlank()){
            inputEmail.error = "Campo obrigatório"
            inputEmail.isErrorEnabled = true
        } else if (!textoEmail.matches(Regex(".+@.+\\..+"))){
            inputEmail.error = "Formato deve ser usuario@dominio.com"
            inputEmail.isErrorEnabled = true
        } else inputEmail.isErrorEnabled = false

    }

    private fun validaTelefone() {
        val inputTelefone = viewInflated.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_telefone)
        val textoTelefone = inputTelefone.editText?.text.toString()

        if (textoTelefone.isBlank()){
            inputTelefone.error = "Campo obrigatório"
            inputTelefone.isErrorEnabled = true
        } else if (textoTelefone.length!=11){
            inputTelefone.error = "O telefone deve ter 11 digitos com DDD"
            inputTelefone.isErrorEnabled = true
        } else {
            inputTelefone.isErrorEnabled = false
        }
    }

    private fun temErro(id: Int) =
        viewInflated.findViewById<TextInputLayout>(id).isErrorEnabled
}
