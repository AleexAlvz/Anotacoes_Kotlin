package br.com.alexalves.anotacoes_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import br.com.alexalves.anotacoes_kotlin.R

class PerfilUsuarioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false)

        val buttonLogout = view.findViewById<Button>(R.id.perfil_fragment_button_logout)
        buttonLogout.setOnClickListener {
            activity?.finish()
        }

        return view
    }

}
