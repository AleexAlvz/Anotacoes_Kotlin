package br.com.alexalves.anotacoes_kotlin.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.view.fragments.AdicionarAnotacaoFragment
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
        trocarFragment(anotacaoId, detalheAnotacaoFragmentConfigurado())
    }

    private fun trocarFragment(anotacaoId: String, fragment: Fragment) {

        val bundle = Bundle()
        bundle.putString(getString(R.string.anotacaoIdArgument), anotacaoId)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.detalhe_anotacao_fragment_container, fragment, null)
            .commit()
    }

    private fun detalheAnotacaoFragmentConfigurado(): Fragment{
        val detalheAnotacaoFragment = DetalheAnotacaoFragment()
        detalheAnotacaoFragment.listenerEditAnotacaoButton = {
            trocarFragment(anotacaoId, adicionaAnotacaoFragmentConfigurado())
        }
        detalheAnotacaoFragment.listenerVoltarParaAnotacoesFragment = {
            val usuarioEmail = intent.getStringExtra(getString(R.string.usuarioEmailArgument))
            val intent = Intent(this, AnotacoesActivity::class.java)
                .putExtra(getString(R.string.usuarioEmailArgument), usuarioEmail)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        return detalheAnotacaoFragment
    }

    private fun adicionaAnotacaoFragmentConfigurado(): Fragment {
        val adicionarAnotacaoFragment = AdicionarAnotacaoFragment()
        adicionarAnotacaoFragment.listenerConfirmaEditButton = {
            trocarFragment(anotacaoId, detalheAnotacaoFragmentConfigurado())
        }
        adicionarAnotacaoFragment.anotacaoId = anotacaoId.toLong()
        return adicionarAnotacaoFragment
    }


}