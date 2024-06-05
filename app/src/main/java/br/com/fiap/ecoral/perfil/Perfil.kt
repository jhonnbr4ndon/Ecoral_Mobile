package br.com.fiap.ecoral.perfil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.fiap.ecoral.LoginActivity
import br.com.fiap.ecoral.R

class Perfil : Fragment() {

    private lateinit var nomeUsuarioTextView: TextView
    private lateinit var emailUsuarioTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        val gerenciaContaLayout = view.findViewById<LinearLayout>(R.id.gerenciaConta)
        val sairContaLayout = view.findViewById<TextView>(R.id.sairConta)

        nomeUsuarioTextView = view.findViewById(R.id.nomeUsuario)
        emailUsuarioTextView = view.findViewById(R.id.emailUsuario)

        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val nomeUsuario = sharedPreferences.getString("nome", "") ?: ""
        val emailUsuario = sharedPreferences.getString("email", "") ?: ""

        nomeUsuarioTextView.text = nomeUsuario
        emailUsuarioTextView.text = emailUsuario

        gerenciaContaLayout.setOnClickListener {
            val intent = Intent(activity, GerenciarContaActivity::class.java)
            startActivity(intent)
        }

        sairContaLayout.setOnClickListener {
            Log.d("PerfilFragment", "Botão sairContaLayout pressionado. Iniciando LoginActivity.")

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}
