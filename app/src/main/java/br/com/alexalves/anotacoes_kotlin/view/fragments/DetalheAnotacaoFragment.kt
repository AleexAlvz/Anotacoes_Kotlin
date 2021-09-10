package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.viewmodel.DetalheAnotacaoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text
import java.util.*

class DetalheAnotacaoFragment : Fragment() {

    private lateinit var anotacaoIdString: String
    val detalheAnotacaoViewModel: DetalheAnotacaoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalhe_anotacao, container, false)
        anotacaoIdString = arguments?.get(getString(R.string.anotacaoIdArgument)).toString()

        configuraObserverAnotacao(view)

        detalheAnotacaoViewModel.buscaAnotacaoPorId(anotacaoIdString.toLong())

        return view
    }

    private fun configuraObserverAnotacao(view: View) {
        detalheAnotacaoViewModel.anotacaoEscolhida.observe(viewLifecycleOwner, Observer { anotacao ->
            if (anotacao != null){
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_titulo).setText(anotacao.titulo)
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_descricao).setText(anotacao.descricao)
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_data).setText(anotacao.dataCriacao.toString())
                view.findViewById<TextView>(R.id.fragment_detalhe_text_input_status).setText(anotacao.status.toString())
            }
        })
    }

}
