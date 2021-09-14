package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.EnumStatus
import br.com.alexalves.anotacoes_kotlin.view.activities.AnotacoesActivity
import br.com.alexalves.anotacoes_kotlin.view.activities.DetalhesAnotacaoActivity
import br.com.alexalves.anotacoes_kotlin.viewmodel.AdicionarAnotacaoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AdicionarAnotacaoFragment : Fragment() {

    var listenerConfirmaEditButton: (()->Unit)? = null
    var anotacaoId: Long? = null
    var anotacaoEditar: Anotacao? = null
    lateinit var textTituloFragmnet: TextView
    lateinit var usuarioEmail: String
    lateinit var inputTitulo: TextInputLayout
    lateinit var inputDescricao: TextInputLayout
    lateinit var buttonCriarAnotacao: Button
    lateinit var buttonLimparCampos: Button
    lateinit var spinnerStatus: Spinner
    private val adicionarAnotacaoViewModel: AdicionarAnotacaoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_adicionar_anotacao, container, false)

        configuraObserverAnotacao()
        iniciaParametros(view)
        configuraButtonLimparCampos(view)
        configuraButtonCriarAnotacao(view)

        return view
    }

    private fun configuraObserverAnotacao() {
        adicionarAnotacaoViewModel.anotacaoParaEditar.observe(viewLifecycleOwner, androidx.lifecycle.Observer { anotacao ->
            if (anotacao != null){
                anotacaoEditar = anotacao
                textTituloFragmnet.setText("Editar anotação")
                inputTitulo.editText?.setText(anotacao.titulo)
                inputDescricao.editText?.setText(anotacao.descricao)
                buttonCriarAnotacao.setText("Editar anotação")
                spinnerStatus.visibility = View.VISIBLE
            }
        })
    }

    private fun iniciaParametros(view: View) {
        textTituloFragmnet = view.findViewById(R.id.text_titulo_adicionar_anotacao_fragment)
        usuarioEmail = arguments?.getString(getString(R.string.usuarioEmailArgument)).toString()
        inputTitulo = view.findViewById(R.id.text_input_titulo_adicionar_anotacao)
        inputDescricao = view.findViewById(R.id.text_input_descricao_adicionar_anotacao)

        //Configura Spinner
        spinnerStatus = view.findViewById(R.id.spinner_status_adicionar_anotacao_fragment)
        spinnerStatus.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, EnumStatus.values())

        if (anotacaoId != null){
            adicionarAnotacaoViewModel.buscaAnotacaoPorId(anotacaoId!!)
        }

    }

    private fun configuraButtonCriarAnotacao(
        view: View,
    ) {
        buttonCriarAnotacao =
            view.findViewById<Button>(R.id.button_adicionar_nova_anotacao)

        buttonCriarAnotacao.setOnClickListener {
            val titulo = inputTitulo.editText?.text.toString()
            val descricao = inputDescricao.editText?.text.toString()

            if (!(titulo.isBlank() || descricao.isBlank())) {
                if (anotacaoEditar != null){
                    anotacaoEditar?.titulo = titulo
                    anotacaoEditar?.descricao = descricao
                    anotacaoEditar?.status = spinnerStatus.selectedItem as EnumStatus
                    adicionarAnotacaoViewModel.criarAnotacao(anotacaoEditar!!)
                    listenerConfirmaEditButton?.invoke()
                } else {
                    val novaAnotacao = Anotacao(usuarioEmail, titulo, descricao,
                        Calendar.getInstance(), EnumStatus.PENDENTE)
                    adicionarAnotacaoViewModel.criarAnotacao(novaAnotacao)
                    val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
                    bottomNavigation?.selectedItemId = R.id.navigation_item_anotacoes
                }
            } else {
                Toast.makeText(context, "Campos não podem estar vazios", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun configuraButtonLimparCampos(
        view: View,
    ) {
        buttonLimparCampos =
            view.findViewById(R.id.button_limpar_campos_nova_anotacao)

        buttonLimparCampos.setOnClickListener {
            limparCampos()
        }
    }

    private fun limparCampos() {
        inputTitulo.editText?.text = null
        inputDescricao.editText?.text = null
    }

}
