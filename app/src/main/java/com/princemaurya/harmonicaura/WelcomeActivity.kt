package com.princemaurya.harmonicaura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findViewById<Button>(R.id.buttonBegin).setOnClickListener {
            // Example: Go to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
} 