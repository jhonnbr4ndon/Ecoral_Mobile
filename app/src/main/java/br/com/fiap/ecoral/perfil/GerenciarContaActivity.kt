package br.com.fiap.ecoral.perfil

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.GerenciarContaActivityBinding

class GerenciarContaActivity : AppCompatActivity() {

    private lateinit var binding: GerenciarContaActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GerenciarContaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Voltar"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val atualizarNomeLayout = findViewById<LinearLayout>(R.id.atualizarNome)
        val atualizarEmailLayout = findViewById<LinearLayout>(R.id.atualizarEmail)
        val atualizarSenhaLayout = findViewById<LinearLayout>(R.id.atualizarSenha)
        val deletarContaLayout = findViewById<LinearLayout>(R.id.deletarConta)

        atualizarNomeLayout.setOnClickListener {
            val intent = Intent(this, AtualizarNomeActivity::class.java)
            startActivity(intent)
        }

        atualizarEmailLayout.setOnClickListener {
            val intent = Intent(this, AtualizarEmailActivity::class.java)
            startActivity(intent)
        }

        atualizarSenhaLayout.setOnClickListener {
            val intent = Intent(this, AtualizarSenhaActivity::class.java)
            startActivity(intent)
        }

        deletarContaLayout.setOnClickListener {
            val intent = Intent(this, DeletarContaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
