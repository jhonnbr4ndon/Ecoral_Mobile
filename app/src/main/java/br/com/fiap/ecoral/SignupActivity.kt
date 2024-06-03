package br.com.fiap.ecoral

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecoral.databinding.ActivitySignupBinding
import br.com.fiap.ecoral.entity.ApiClient
import br.com.fiap.ecoral.entity.ApiService
import br.com.fiap.ecoral.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.voltarLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signupButton.setOnClickListener {
            val nome = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val senha = binding.editTextPassword.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this@SignupActivity, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEmailValid(email)) {
                Toast.makeText(this@SignupActivity, "Por favor insira um endereço de e-mail válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha.length < 8) {
                Toast.makeText(this@SignupActivity, "A senha deve ter pelo menos 8 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiService = ApiClient.retrofit.create(ApiService::class.java)

            val user = User(null,nome, email, senha)

            apiService.createUser(user).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "Cadastro bem sucedido", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@SignupActivity, "Falha no Cadastro", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, "Falha no Cadastro: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}
