package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.viewmodel.CriarContaViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class CriarContaFragment : Fragment() {

    private val criarContaViewModel by inject<CriarContaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_criar_conta, container, false)

        verificaNome(view)
        verificaSenha(view)
        verificaConfirmarSenha(view)
        verificaCpf(view)
        verificaDataNascimento(view)

        return view
    }

    private fun verificaNome(view: View) {
        val textInputNome = view.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_nome)
        textInputNome.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeNome(
                hasFocus,
                textInputNome
            )
        }
    }

    private fun verificaSenha(view: View) {
        val textInputSenha =
            view.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha)
        textInputSenha.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeSenha(
                hasFocus,
                textInputSenha
            )
        }
    }

    private fun verificaConfirmarSenha(
        view: View
    ) {
        val textInputSenha = view.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_senha)
        val textInputConfirmarSenha = view.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_confirmar_senha)
        textInputConfirmarSenha.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeConfirmarSenha(
                hasFocus,
                textInputConfirmarSenha,
                textInputSenha
            )
        }
    }

    private fun verificaCpf(view: View) {
        val textInputCpf = view.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_cpf)
        textInputCpf.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeCpf(
                hasFocus,
                textInputCpf
            )
        }
    }

    private fun verificaDataNascimento(view: View) {
        val textInputDataNascimento =
            view.findViewById<TextInputLayout>(R.id.criar_conta_fragment_input_nascimento)
        textInputDataNascimento.editText?.setOnFocusChangeListener { v, hasFocus ->
            onFocusChangeDataNascimento(
                hasFocus,
                textInputDataNascimento
            )
        }
    }

    private fun onFocusChangeCpf(hasFocus: Boolean, textInputCpf: TextInputLayout?) {

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

    private fun onFocusChangeDataNascimento(
        hasFocus: Boolean,
        textInput: TextInputLayout
    ) {
        val texto = textInput.editText?.text.toString()
        if (!hasFocus) {
            if (texto.isNullOrBlank()) {
                textInput.error = "A data de nascimento não pode estar vazia"
                textInput.isErrorEnabled = true
            } else if (!(texto.length == 10)) {
                textInput.error = "O formato da data deve ser 'dd/mm/yyyy'"
                textInput.isErrorEnabled = true
            } else if (
                !(texto[2].equals("/")) ||
                !(texto[5].equals("/"))
            ) {
                textInput.error = "A data deve ser separada por '/'"
                textInput.isErrorEnabled = true
            } else textInput.isErrorEnabled = false
        }
    }

}
