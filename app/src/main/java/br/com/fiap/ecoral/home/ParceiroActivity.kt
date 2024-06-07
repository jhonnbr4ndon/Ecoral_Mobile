package br.com.fiap.ecoral.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.ActivityParceiroBinding

class ParceiroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParceiroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParceiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Definindo o título da Toolbar como "Voltar"
        supportActionBar?.title = "Voltar"

        // Habilitando o botão de voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}