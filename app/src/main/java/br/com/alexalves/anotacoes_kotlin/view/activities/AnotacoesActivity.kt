package br.com.alexalves.anotacoes_kotlin.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.view.fragments.AdicionarAnotacaoFragment
import br.com.alexalves.anotacoes_kotlin.view.fragments.AnotacoesFragment
import br.com.alexalves.anotacoes_kotlin.view.fragments.PerfilUsuarioFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnotacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anotacoes)

        trocarFragment(AnotacoesFragment())

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigation.setOnItemSelectedListener { item ->

            when(item.itemId){
                R.id.navigation_item_anotacoes-> {
                    trocarFragment(AnotacoesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_item_adicionar->{
                    trocarFragment(AdicionarAnotacaoFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_item_perfil->{
                    trocarFragment(PerfilUsuarioFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }

    }

    fun trocarFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.anotacoes_fragment_container, fragment, null)
            .commit()

    }
}