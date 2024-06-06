package br.com.fiap.ecoral

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.fiap.ecoral.perfil.GerenciarContaActivity

class Home : Fragment() {

    private lateinit var nomeUsuarioTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val relatorioLayout = view.findViewById<LinearLayout>(R.id.relatorio)
        val parceiroLayout = view.findViewById<LinearLayout>(R.id.parceiro)
        nomeUsuarioTextView = view.findViewById(R.id.nomeUsuario)

        relatorioLayout.setOnClickListener {
            val intent = Intent(activity, RelatorioActivity::class.java)
            startActivity(intent)
        }

        parceiroLayout.setOnClickListener {
            val intent = Intent(activity, ParceiroActivity::class.java)
            startActivity(intent)
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperar o nome do usuário dos SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val nomeUsuario = sharedPreferences.getString("nome", "")

        // Atualizar o TextView com o nome do usuário
        nomeUsuarioTextView.text = nomeUsuario
    }
}
