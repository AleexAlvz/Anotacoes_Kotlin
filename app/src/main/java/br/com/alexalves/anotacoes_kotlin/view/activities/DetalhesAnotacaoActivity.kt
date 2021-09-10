package br.com.alexalves.anotacoes_kotlin.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.view.fragments.DetalheAnotacaoFragment

class DetalhesAnotacaoActivity: AppCompatActivity() {

    private lateinit var anotacaoId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_perfil)
        configuraDetalhesAnotacao()
    }

    private fun configuraDetalhesAnotacao() {
        anotacaoId = intent.getLongExtra(getString(R.string.anotacaoIdArgument), 0L).toString()


        trocarFragment(anotacaoId, DetalheAnotacaoFragment())

    }

    private fun trocarFragment(anotacaoId: String, fragment: Fragment) {

        val bundle = Bundle()
        bundle.putString(getString(R.string.anotacaoIdArgument), anotacaoId)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.detalhe_anotacao_fragment_container, fragment, null)
            .commit()
    }

}