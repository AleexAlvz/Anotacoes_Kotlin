package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.database.UsuarioDAO
import br.com.alexalves.anotacoes_kotlin.view.activities.AnotacoesActivity
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private lateinit var inputTextEmail: TextInputLayout
    private lateinit var inputTextSenha: TextInputLayout
    private lateinit var viewFragment: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewFragment = inflater.inflate(R.layout.fragment_login, container, false)

        configuraLoginFragment(viewFragment)

        return viewFragment
    }

    private fun configuraLoginFragment(view: View) {
        configuraCampos()
        configuraLoginClickListener()
        configuraCriarContaClickListener()
    }

    private fun configuraCampos() {
        inputTextEmail = viewFragment.findViewById(R.id.login_fragment_text_input_layout_email)
        inputTextSenha = viewFragment.findViewById(R.id.login_fragment_text_input_layout_senha)
    }

    private fun configuraLoginClickListener() {
        val buttonLogin = viewFragment.findViewById<Button>(R.id.login_fragment_button_login)
        buttonLogin.setOnClickListener {
            val intent = Intent(activity, AnotacoesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configuraCriarContaClickListener() {
        val buttonCriarConta = viewFragment.findViewById<Button>(R.id.login_fragment_button_criar_conta)
        buttonCriarConta.setOnClickListener {
            activity?.let { it.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.login_fragment_container, CriarContaFragment(), null)
                .commit()
            }
        }

    }


}


