package com.example.proyect01.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import com.example.proyect01.R
import com.example.proyect01.databinding.ActivityMainBinding
import com.example.proyect01.fragment.SignFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class Login : AppCompatActivity() {
    private lateinit var mBbinding: ActivityMainBinding
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isloadding: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnSing = findViewById<Button>(R.id.btnSignUp)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        emailEditText = findViewById(R.id.etUserNameLogin)
        passwordEditText = findViewById(R.id.etpasswordLogin)

        btnLogin.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Llama a signInWithEmailAndPassword aquí
                signIn(email, password)


            } else {
                // Maneja el caso en que el correo electrónico o la contraseña estén vacíos
                Toast.makeText(
                    this,
                    "El correo electrónico y la contraseña son obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
            }


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

    // Función para iniciar sesión
    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso, el usuario está autenticado
                    val user: FirebaseUser? = mAuth.currentUser
                    isloadding = true
                    // Puedes redirigir a la actividad principal o realizar otras acciones aquí
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Error en el inicio de sesión, manejar el error
                    val exception = task.exception
                    // Mostrar un mensaje de error o realizar acciones según sea necesario
                }
            }
    }


}

