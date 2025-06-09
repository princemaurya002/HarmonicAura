package com.princemaurya.harmonicaura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.UserProfileChangeRequest
import com.princemaurya.harmonicaura.auth.FirebaseAuthManager
import com.princemaurya.harmonicaura.utils.AnimationUtils
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class SignupActivity : AppCompatActivity() {
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
        
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth Manager
        authManager = FirebaseAuthManager.getInstance(this)

        // Find views
        val editTextFirstName = findViewById<EditText>(R.id.editTextFirstName)
        val editTextLastName = findViewById<EditText>(R.id.editTextLastName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonSignup = findViewById<Button>(R.id.buttonSignup)
        val buttonGuest = findViewById<TextView>(R.id.buttonGuest)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)
        val googleSignInCard = findViewById<CardView>(R.id.buttonGoogleSignIn)
        val googleSignInButton = findViewById<SignInButton>(R.id.btn_google_signin)
        progressBar = findViewById(R.id.progressBar)

        // Apply entrance animations
        applyEntranceAnimations()

        // Signup button click
        buttonSignup.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                val firstName = editTextFirstName.text.toString()
                val lastName = editTextLastName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                val confirmPassword = editTextConfirmPassword.text.toString()

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
                    password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@animateButtonClick
                }

                if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@animateButtonClick
                }

                if (password.length < 6) {
                    Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@animateButtonClick
                }

                showLoading(true)
                authManager.createUserWithEmailAndPassword(
                    email = email,
                    password = password,
                    onSuccess = {
                        // Update user profile with full name
                        val fullName = if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                            "$firstName $lastName"
                        } else if (firstName.isNotEmpty()) {
                            firstName
                        } else if (lastName.isNotEmpty()) {
                            lastName
                        } else {
                            // Try to derive name from email
                            val emailName = email.substringBefore('@')
                                .replace(Regex("[^a-zA-Z]"), "")
                                .replaceFirstChar { it.uppercase() }
                            if (emailName.isNotEmpty()) emailName else "Sir/Ma'am"
                        }

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build()

                        authManager.getCurrentUser()?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                showLoading(false)
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Account created successfully! Please verify your email.", Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to update profile: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    },
                    onError = { errorMessage ->
                        showLoading(false)
                        Toast.makeText(this, "Signup failed: $errorMessage", Toast.LENGTH_SHORT).show()
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

        // Login text click
        textViewLogin.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun applyEntranceAnimations() {
        // Animate form elements with staggered delay
        val editTextFirstName = findViewById<EditText>(R.id.editTextFirstName)
        val editTextLastName = findViewById<EditText>(R.id.editTextLastName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonSignup = findViewById<Button>(R.id.buttonSignup)
        val googleSignInCard = findViewById<CardView>(R.id.buttonGoogleSignIn)
        val buttonGuest = findViewById<TextView>(R.id.buttonGuest)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        editTextFirstName?.let { AnimationUtils.animateSlideUp(it, 100) }
        editTextLastName?.let { AnimationUtils.animateSlideUp(it, 150) }
        editTextEmail?.let { AnimationUtils.animateSlideUp(it, 200) }
        editTextPassword?.let { AnimationUtils.animateSlideUp(it, 250) }
        editTextConfirmPassword?.let { AnimationUtils.animateSlideUp(it, 300) }
        buttonSignup?.let { AnimationUtils.animateSlideUp(it, 350) }
        googleSignInCard?.let { AnimationUtils.animateSlideUp(it, 400) }
        buttonGuest?.let { AnimationUtils.animateSlideUp(it, 450) }
        textViewLogin?.let { AnimationUtils.animateSlideUp(it, 500) }
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
        progressBar.visibility = if (show) ProgressBar.VISIBLE else ProgressBar.GONE
    }
}
