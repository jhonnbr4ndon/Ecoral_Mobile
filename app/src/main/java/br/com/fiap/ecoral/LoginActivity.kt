package br.com.fiap.ecoral

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecoral.databinding.ActivityLoginBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
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

            val user = User(null,"", email, password)

            apiService.loginUser(user).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Login bem-sucedido", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Falha no login: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.signupText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}
