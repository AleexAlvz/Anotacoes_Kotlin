package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.viewmodel.DetalheAnotacaoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text
import java.util.*

class DetalheAnotacaoFragment : Fragment() {

    private lateinit var inflatedView: View
    private lateinit var anotacaoIdString: String
    val detalheAnotacaoViewModel: DetalheAnotacaoViewModel by viewModel()
    var listenerEditAnotacaoButton: (()->Unit)? = null
    var listenerVoltarParaAnotacoesFragment: (()->Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.fragment_detalhe_anotacao, container, false)
        anotacaoIdString = arguments?.get(getString(R.string.anotacaoIdArgument)).toString()

        configuraObserverAnotacao(inflatedView)
        detalheAnotacaoViewModel.buscaAnotacaoPorId(anotacaoIdString.toLong())

        configuraButtonVoltar(inflatedView)
        configuraButtonEditarAnotacao(inflatedView)

        return inflatedView
    }

    private fun configuraButtonEditarAnotacao(view: View) {
        view.findViewById<TextView>(R.id.fragment_detalhe_button_editar).setOnClickListener {
            listenerEditAnotacaoButton?.invoke()
        }

    }

    private fun configuraButtonVoltar(view: View) {
        view.findViewById<TextView>(R.id.fragment_detalhe_button_voltar).setOnClickListener { listenerVoltarParaAnotacoesFragment?.invoke() }
    }

    private fun configuraObserverAnotacao(view: View) {
        detalheAnotacaoViewModel.anotacaoEscolhida.observe(viewLifecycleOwner, Observer { anotacao ->
            if (anotacao != null){
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_titulo).setText(anotacao.titulo)
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_descricao).setText(anotacao.descricao)
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_status).setText(anotacao.status.toString())

                //Formata data de criacao da anotacao
                val day = anotacao.dataCriacao.get(Calendar.DAY_OF_MONTH)
                val month = anotacao.dataCriacao.get(Calendar.MONTH)
                val year = anotacao.dataCriacao.get(Calendar.YEAR)
                val dataFormatada = "$day/$month/$year"
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_data).setText(dataFormatada)
            }
        })
    }
}
