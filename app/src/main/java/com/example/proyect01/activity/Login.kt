package com.example.proyect01.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.proyect01.R
import com.example.proyect01.databinding.ActivityMainBinding
import com.example.proyect01.fragment.SignFragment

class Login : AppCompatActivity() {
    private lateinit var mBbinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnSing = findViewById<Button>(R.id.btnSignUp)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val  intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnSing.setOnClickListener { launchSignFragment() }
    }
    private fun launchSignFragment(args: Bundle? = null) {
        val fragment = SignFragment()
        if (args != null) fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.login, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

}