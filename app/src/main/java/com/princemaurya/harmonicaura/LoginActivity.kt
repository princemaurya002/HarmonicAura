package com.princemaurya.harmonicaura

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.common.SignInButton
import com.princemaurya.harmonicaura.auth.FirebaseAuthManager

class LoginActivity : AppCompatActivity() {
    private lateinit var authManager: FirebaseAuthManager
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth Manager
        authManager = FirebaseAuthManager.getInstance(this)

        // Check if user is already logged in
        if (authManager.isUserLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Find views
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonGuest = findViewById<TextView>(R.id.buttonGuest)
        val textViewSignup = findViewById<TextView>(R.id.textViewSignup)
        val textViewForgotPassword = findViewById<TextView>(R.id.textViewForgotPassword)
        val googleSignInCard = findViewById<CardView>(R.id.buttonGoogleSignIn)
        val googleSignInButton = findViewById<SignInButton>(R.id.btn_google_signin)
        progressBar = findViewById(R.id.progressBar)

        // Login button click
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoading(true)
            authManager.signInWithEmailAndPassword(
                email = email,
                password = password,
                onSuccess = {
                    showLoading(false)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                onError = { errorMessage ->
                    showLoading(false)
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Google Sign In button click
        googleSignInButton.setOnClickListener {
            showLoading(true)
            authManager.signInWithGoogle(
                activity = this,
                onSuccess = {
                    showLoading(false)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                onError = { errorMessage ->
                    showLoading(false)
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Guest button click
        buttonGuest.setOnClickListener {
            showLoading(true)
            authManager.signInAnonymously(
                context = this,
                onSuccess = {
                    showLoading(false)
                    Toast.makeText(this, "Welcome Guest!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                onError = { errorMessage ->
                    showLoading(false)
                    Toast.makeText(this, "Guest login failed: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Forgot password click
        textViewForgotPassword.setOnClickListener {
            val email = editTextEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoading(true)
            authManager.sendPasswordResetEmail(
                email = email,
                onSuccess = {
                    showLoading(false)
                    Toast.makeText(this, "Password reset email sent. Please check your inbox.", Toast.LENGTH_LONG).show()
                },
                onError = { errorMessage ->
                    showLoading(false)
                    Toast.makeText(this, "Failed to send reset email: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Signup text click
        textViewSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 9001) {
            authManager.handleGoogleSignInResult(
                data = data,
                onSuccess = {
                    showLoading(false)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                onError = { errorMessage ->
                    showLoading(false)
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
} 