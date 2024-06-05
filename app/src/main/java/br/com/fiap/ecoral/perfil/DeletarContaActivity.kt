package br.com.fiap.ecoral.perfil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.LoginActivity
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.ActivityDeletarContaBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeletarContaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeletarContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeletarContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Voltar"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.deletarConta.setOnClickListener {
            val idUsuario = obterIdUsuarioLogado()

            if (idUsuario != null) {
                deletarUsuario(idUsuario)
                val intent = Intent(this@DeletarContaActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erro ao obter ID do usuário", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obterIdUsuarioLogado(): Long? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("id", -1)
    }

    private fun deletarUsuario(idUsuario: Long) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)

        apiService.deletarUsuario(idUsuario).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DeletarContaActivity, "Conta deletada com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@DeletarContaActivity, "Falha ao deletar conta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DeletarContaActivity, "Erro ao deletar conta: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}