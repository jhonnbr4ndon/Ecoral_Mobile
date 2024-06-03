package br.com.fiap.ecoral

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.fiap.ecoral.databinding.ActivityMainBinding
import br.com.fiap.ecoral.entity.HttpTestTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        val url = "http://192.168.0.55:8080/usuario"
        HttpTestTask().execute(url)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.graficos -> replaceFragment(Grafico())
                R.id.explore -> replaceFragment(Explore())
                R.id.perfil -> replaceFragment(Perfil())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}