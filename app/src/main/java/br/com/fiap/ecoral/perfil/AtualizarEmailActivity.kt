package br.com.fiap.ecoral.perfil

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.ActivityAtualizarEmailBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
import br.com.fiap.ecoral.entity.UsuarioDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtualizarEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Definindo o título da Toolbar como "Voltar"
        supportActionBar?.title = "Voltar"

        // Habilitando o botão de voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.atualizarEmail.setOnClickListener {
            val idUsuario = obterIdUsuarioLogado() // Implemente a lógica para obter o ID do usuário logado
            val nomeUsuario = obterNomeUsuarioLogado()
            val senhaUsuario = obterSenhaUsuarioLogado()

            if (idUsuario != null && nomeUsuario != null && senhaUsuario != null) {
                val novoEmail = binding.emailAtualizado.text.toString()

                val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

                if (!novoEmail.matches(emailRegex.toRegex())) {
                    Toast.makeText(this@AtualizarEmailActivity, "Por favor, insira um email válido", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Log.d("AtualizarEmailActivity", "ID do usuário: $idUsuario")
                Log.d("AtualizarEmailActivity", "Nome do usuário: $nomeUsuario")
                Log.d("AtualizarEmailActivity", "Novo Email: $novoEmail")
                Log.d("AtualizarEmailActivity", "Senha do usuário: $senhaUsuario")

                atualizarUsuario(idUsuario, nomeUsuario, novoEmail, senhaUsuario)
            } else {
                Toast.makeText(this, "Erro ao obter informações do usuário", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obterIdUsuarioLogado(): Long? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("id", -1) // Retorna -1 se o ID não estiver disponível
    }

    private fun obterNomeUsuarioLogado(): String? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("nome", null) // Retorna null se o nome não estiver disponível
    }

    private fun obterSenhaUsuarioLogado(): String? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("senha", null) // Retorna null se a senha não estiver disponível
    }

    private fun atualizarUsuario(idUsuario: Long, nomeUsuario: String, novoEmail: String, senhaUsuario: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val novoUsuarioDTO = UsuarioDTO(nomeUsuario, novoEmail, senhaUsuario)

        apiService.atualizarNomeUsuario(idUsuario, novoUsuarioDTO).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("AtualizarEmailActivity", "Usuário atualizado com sucesso")
                    Toast.makeText(this@AtualizarEmailActivity, "Usuário atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.e("AtualizarEmailActivity", "Falha ao atualizar o usuário: ${response.message()}")
                    Toast.makeText(this@AtualizarEmailActivity, "Falha ao atualizar o usuário", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AtualizarEmailActivity", "Erro ao atualizar o usuário: ${t.message}", t)
                Toast.makeText(this@AtualizarEmailActivity, "Erro ao atualizar o usuário: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}