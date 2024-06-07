package br.com.fiap.ecoral.home

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.ActivityRelatorioBinding

class RelatorioActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRelatorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Definindo o título da Toolbar como "Voltar"
        supportActionBar?.title = "Voltar"

        // Habilitando o botão de voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gerarRelatorioButton: Button = findViewById(R.id.gerarRelatorio)

        gerarRelatorioButton.setOnClickListener {
            // Exibir o alerta
            exibirAlerta()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun exibirAlerta() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Relatório Gerado")
        alertDialogBuilder.setMessage("Relatório Gerado com Sucesso!")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            // Implemente a lógica para o botão OK, se necessário
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}