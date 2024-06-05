package br.com.fiap.ecoral.perfil

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.fiap.ecoral.R
import br.com.fiap.ecoral.databinding.ActivityAtualizarNomeBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
import br.com.fiap.ecoral.entity.UsuarioDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtualizarNomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarNomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarNomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Voltar"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.atualizarNome.setOnClickListener {
            val idUsuario = obterIdUsuarioLogado() // Implemente a lógica para obter o ID do usuário logado
            val emailUsuario = obterEmailUsuarioLogado()
            val senhaUsuario = obterSenhaUsuarioLogado()

            if (idUsuario != null && emailUsuario != null && senhaUsuario != null) {
                val novoNome = binding.nomeAtualizado.text.toString()

                if (novoNome.length > 25) {
                    Toast.makeText(this@AtualizarNomeActivity, "O nome deve ter no máximo 25 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Log.d("AtualizarNomeActivity", "ID do usuário: $idUsuario")
                Log.d("AtualizarNomeActivity", "Novo nome: $novoNome")
                Log.d("AtualizarNomeActivity", "Email do usuário: $emailUsuario")
                Log.d("AtualizarNomeActivity", "Senha do usuário: $senhaUsuario")

                atualizarUsuario(idUsuario, novoNome, emailUsuario, senhaUsuario)
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

    private fun obterEmailUsuarioLogado(): String? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null) // Retorna null se o email não estiver disponível
    }

    private fun obterSenhaUsuarioLogado(): String? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("senha", null) // Retorna null se a senha não estiver disponível
    }

    private fun atualizarUsuario(idUsuario: Long, novoNome: String, emailUsuario: String, senhaUsuario: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val novoUsuarioDTO = UsuarioDTO(novoNome, emailUsuario, senhaUsuario)

        apiService.atualizarNomeUsuario(idUsuario, novoUsuarioDTO).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("AtualizarNomeActivity", "Usuário atualizado com sucesso")
                    Toast.makeText(this@AtualizarNomeActivity, "Usuário atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.e("AtualizarNomeActivity", "Falha ao atualizar o usuário: ${response.message()}")
                    Toast.makeText(this@AtualizarNomeActivity, "Falha ao atualizar o usuário", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AtualizarNomeActivity", "Erro ao atualizar o usuário: ${t.message}", t)
                Toast.makeText(this@AtualizarNomeActivity, "Erro ao atualizar o usuário: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
