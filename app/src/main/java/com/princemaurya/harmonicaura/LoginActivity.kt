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
import com.princemaurya.harmonicaura.utils.AnimationUtils
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var authManager: FirebaseAuthManager
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure window for transparent status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true // Light status bar icons
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
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

        // Apply entrance animations
        applyEntranceAnimations()

        // Login button click
        buttonLogin.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@animateButtonClick
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
        }

        // Google Sign In button click
        googleSignInButton.setOnClickListener {
            AnimationUtils.animateCardClick(googleSignInCard) {
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
        }

        // Guest button click
        buttonGuest.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
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
        }

        // Forgot password click
        textViewForgotPassword.setOnClickListener {
            AnimationUtils.createRippleEffect(it) {
                val email = editTextEmail.text.toString()
                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
                    return@createRippleEffect
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
        }

        // Signup text click
        textViewSignup.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                startActivity(Intent(this, SignupActivity::class.java))
            }
        }
    }

    private fun applyEntranceAnimations() {
        // Animate form elements with staggered delay
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val googleSignInCard = findViewById<CardView>(R.id.buttonGoogleSignIn)
        val buttonGuest = findViewById<TextView>(R.id.buttonGuest)
        val textViewSignup = findViewById<TextView>(R.id.textViewSignup)
        val textViewForgotPassword = findViewById<TextView>(R.id.textViewForgotPassword)

        editTextEmail?.let { AnimationUtils.animateSlideUp(it, 100) }
        editTextPassword?.let { AnimationUtils.animateSlideUp(it, 200) }
        buttonLogin?.let { AnimationUtils.animateSlideUp(it, 300) }
        googleSignInCard?.let { AnimationUtils.animateSlideUp(it, 400) }
        buttonGuest?.let { AnimationUtils.animateSlideUp(it, 500) }
        textViewForgotPassword?.let { AnimationUtils.animateSlideUp(it, 600) }
        textViewSignup?.let { AnimationUtils.animateSlideUp(it, 700) }
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