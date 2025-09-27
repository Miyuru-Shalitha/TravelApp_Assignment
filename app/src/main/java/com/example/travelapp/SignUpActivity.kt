package com.example.travelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstNameEditText = findViewById<EditText>(R.id.edit_text_first_name)
        val lastNameEditText = findViewById<EditText>(R.id.edit_text_last_name)
        val emailEditText = findViewById<EditText>(R.id.edit_text_email)
        val passwordEditText = findViewById<EditText>(R.id.edit_text_password)
        val signUpButton = findViewById<Button>(R.id.button_sign_up)

        signUpButton.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val currentUser = FirebaseAuth.getInstance().currentUser

                        if (currentUser != null) {
                            val user = hashMapOf(
                                "firstName" to firstNameEditText.text.toString(),
                                "lastName" to lastNameEditText.text.toString()
                            )

                            Firebase.firestore.collection("users")
                                .document(currentUser.uid)
                                .set(user)
                                .addOnCompleteListener { userCreationTask ->
                                    if (userCreationTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Successfully signed up!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Failed to sign up!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                        }

                        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        val errorMessage =
                            when ((task.exception as? FirebaseAuthException)?.errorCode) {
                                "ERROR_EMAIL_ALREADY_IN_USE" -> "Email is already registered."
                                "ERROR_INVALID_EMAIL" -> "Invalid email format."
                                "ERROR_WEAK_PASSWORD" -> "Password is too weak (minimum 6 characters)."
                                else -> "Sign-up failed: ${task.exception?.message}"
                            }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}