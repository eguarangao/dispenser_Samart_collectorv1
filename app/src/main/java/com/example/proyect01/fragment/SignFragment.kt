package com.example.proyect01.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.proyect01.R
import com.example.proyect01.activity.Login
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignFragment : Fragment() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        // Vincula las vistas desde el diseño XML
        emailEditText = view.findViewById(R.id.etUserNameCreate)
        passwordEditText = view.findViewById(R.id.etpasswordCreate)
        firstNameEditText = view.findViewById(R.id.etFirstName)
        lastNameEditText = view.findViewById(R.id.etLastName)
        registerButton = view.findViewById(R.id.btnSignUp)
        // Configura el OnClickListener para el botón de registro
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()

            // Llama a la función de registro
            createAccountWithEmail(email, password, firstName, lastName)
        }

        return view
    }

    private fun createAccountWithEmail(
        email: String,
        password: String,
        firshName: String,
        lastName: String
    ) {
        progressBar.visibility = View.VISIBLE
        if (email != null && !email.isEmpty() && isValidEmail(email)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Registro con éxito
                        val user: FirebaseUser? = mAuth.currentUser;
                        if (user != null) {
                            val userUID = user.uid
                            val userData = hashMapOf(
                                "firshName" to firshName,
                                "lastName" to lastName,
                                "email" to email // Agrega el correo electrónico si lo necesitas
                            )

                            db.collection("Usuarios").document(userUID).set(userData)
                                .addOnSuccessListener(OnSuccessListener<Void?> {
                                    // Datos de usuario guardados con éxito
                                    // Redirigir a la actividad principal o realizar otras acciones
                                    val intent = Intent(requireContext(), Login::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                })
                                .addOnFailureListener(OnFailureListener {
                                    // Error al guardar datos de usuario en Firestore
                                    // Manejar el error según sea necesario
                                    Toast.makeText(
                                        context,
                                        "Error al guardar datos de usuario en Firestore",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                    } else {
                        // Error en el registro con correo y contraseña
                        Toast.makeText(
                            context,
                            "Error en el registro con correo y contraseña: " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }


    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid = true
        for (textField in textFields) {
            if (textField.editText?.text.toString().trim().isEmpty()) {
                textField.error = getString(R.string.helper_required)
                isValid = false
            } else textField.error = null
        }
        if (!isValid)
            Toast.makeText(
                context,
                R.string.edit_dispenser_message_Valid,
                Toast.LENGTH_SHORT
            ).show()
        return isValid
    }
}