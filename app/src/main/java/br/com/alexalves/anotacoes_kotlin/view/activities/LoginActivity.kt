package br.com.alexalves.anotacoes_kotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.view.fragments.LoginFragment
import br.com.alexalves.anotacoes_kotlin.view.fragments.SplashFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iniciaApp()

    }

    private fun iniciaApp() {
        trocarFragment(SplashFragment())
        CoroutineScope(Main).launch {
            delay(2000L)
            trocarFragment(LoginFragment())
        }
    }

    fun trocarFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.login_fragment_container, fragment, null)
            .commit()
    }

}