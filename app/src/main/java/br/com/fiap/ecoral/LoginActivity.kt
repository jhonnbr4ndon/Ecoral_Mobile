package br.com.fiap.ecoral

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecoral.databinding.ActivityLoginBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
import br.com.fiap.ecoral.entity.Credentials
import br.com.fiap.ecoral.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEmailValid(email)) {
                Toast.makeText(this@LoginActivity, "Por favor insira um endereço de e-mail válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                Toast.makeText(this@LoginActivity, "A senha deve ter pelo menos 8 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiService = ApiClient.retrofit.create(ApiService::class.java)

//            val user = User(null,"", email, password)

            val credentials = Credentials(email, password)

            apiService.loginUser(credentials).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            // Aqui você pode acessar os dados do usuário
                            Log.d("LoginActivity", "Login bem-sucedido")
                            Log.d("LoginActivity", "ID: ${user.id}")
                            Log.d("LoginActivity", "Nome: ${user.nome}")
                            Log.d("LoginActivity", "Email: ${user.email}")
                            Log.d("LoginActivity", "Senha: ${user.senha}")

                            Toast.makeText(this@LoginActivity, "Login bem-sucedido", Toast.LENGTH_SHORT).show()

                            // Guardando os dados do usuario logado
                            saveUserData(user)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // Resposta vazia ou nula
                            Log.e("LoginActivity", "Resposta vazia ou nula após login")
                            Toast.makeText(this@LoginActivity, "Resposta vazia ou nula após login", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Resposta não bem-sucedida
                        Log.e("LoginActivity", "Falha no login: ${response.message()}")
                        Toast.makeText(this@LoginActivity, "Falha no login: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    // Erro na requisição
                    Log.e("LoginActivity", "Erro durante a solicitação de login: ${t.message}", t)
                    Toast.makeText(this@LoginActivity, "Erro durante a solicitação de login: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })


        }

        binding.signupText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUserData(user: User) {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putLong("id", user.id ?: -1L)
        editor.putString("nome", user.nome)
        editor.putString("email", user.email)
        editor.putString("senha", user.senha)

        editor.apply()
        Log.d("LoginActivity", "Dados do usuário salvos com sucesso: ID=${user.id}, Nome=${user.nome}, Email=${user.email}, Senha=${user.senha}")
    }


    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}
