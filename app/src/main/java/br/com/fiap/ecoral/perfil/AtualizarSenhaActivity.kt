package br.com.fiap.ecoral.perfil

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.ActivityAtualizarSenhaBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
import br.com.fiap.ecoral.entity.UsuarioDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtualizarSenhaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAtualizarSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Definindo o título da Toolbar como "Voltar"
        supportActionBar?.title = "Voltar"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.atualizarSenha.setOnClickListener {
            val idUsuario = obterIdUsuarioLogado()
            val nomeUsuario = obterNomeUsuarioLogado()
            val emailUsuario = obterEmailUsuarioLogado()

            if (idUsuario != null && nomeUsuario != null && emailUsuario != null) {
                val novaSenha = binding.senhaAtualizada.text.toString()

                if (novaSenha.length < 8) {
                    Toast.makeText(this@AtualizarSenhaActivity, "A senha deve ter pelo menos 8 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Log.d("AtualizarSenhaActivity", "ID do usuário: $idUsuario")
                Log.d("AtualizarSenhaActivity", "Nome do usuário: $nomeUsuario")
                Log.d("AtualizarSenhaActivity", "Email do usuário: $emailUsuario")
                Log.d("AtualizarSenhaActivity", "Nova senha: $novaSenha")

                atualizarUsuario(idUsuario, nomeUsuario, emailUsuario, novaSenha)
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

    private fun obterEmailUsuarioLogado(): String? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null) // Retorna null se o email não estiver disponível
    }

    private fun atualizarUsuario(idUsuario: Long, nomeUsuario: String, emailUsuario: String, novaSenha: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val novoUsuarioDTO = UsuarioDTO(nomeUsuario, emailUsuario, novaSenha)

        apiService.atualizarNomeUsuario(idUsuario, novoUsuarioDTO).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("AtualizarSenhaActivity", "Usuário atualizado com sucesso")
                    Toast.makeText(this@AtualizarSenhaActivity, "Usuário atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.e("AtualizarSenhaActivity", "Falha ao atualizar o usuário: ${response.message()}")
                    Toast.makeText(this@AtualizarSenhaActivity, "Falha ao atualizar o usuário", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AtualizarSenhaActivity", "Erro ao atualizar o usuário: ${t.message}", t)
                Toast.makeText(this@AtualizarSenhaActivity, "Erro ao atualizar o usuário: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}