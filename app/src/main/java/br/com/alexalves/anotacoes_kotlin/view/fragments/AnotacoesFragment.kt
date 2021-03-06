package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.view.activities.DetalhesAnotacaoActivity
import br.com.alexalves.anotacoes_kotlin.view.adapters.AnotacoesAdapter
import br.com.alexalves.anotacoes_kotlin.viewmodel.AnotacoesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnotacoesFragment : Fragment() {

    private val anotacoesViewModel: AnotacoesViewModel by viewModel()
    private lateinit var usuarioEmail: String
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_anotacoes, container, false)

        recyclerView = view.findViewById(R.id.anotacoes_recyclerview)
        usuarioEmail = arguments?.getString(getString(R.string.usuarioEmailArgument)).toString()

        configuraObserverAnotacoes()

        anotacoesViewModel.buscaAnotacoesPorUsuario(usuarioEmail)

        return view
    }

    private fun configuraObserverAnotacoes() {
        anotacoesViewModel.anotacoesPorUsuario.observe(viewLifecycleOwner, Observer { anotacoes ->
            val sortedList = anotacoes.sortedWith(compareBy({ it.dataCriacao.timeInMillis })).reversed()
            val adapter = AnotacoesAdapter(sortedList, requireContext(), this::onItemClickListener)
            adapter.notifyDataSetChanged()
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        })
    }

    private fun onItemClickListener(anotacao: Anotacao) {
        val intent = Intent(requireContext(), DetalhesAnotacaoActivity::class.java)
        intent.putExtra(getString(R.string.anotacaoIdArgument), anotacao.id)
        intent.putExtra(getString(R.string.usuarioEmailArgument), usuarioEmail)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
