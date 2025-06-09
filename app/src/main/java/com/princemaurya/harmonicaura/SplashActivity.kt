package com.princemaurya.harmonicaura

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.princemaurya.harmonicaura.utils.AnimationUtils
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure window for transparent status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true // Light status bar icons
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
        setContentView(R.layout.activity_splash)

        // Get references to views
        val splashImage = findViewById<ImageView>(R.id.splashImage)
        val byText = findViewById<TextView>(R.id.byText)

        // Apply animations
        AnimationUtils.animateRotateIn(splashImage)
        
        // Delay the text animation
        Handler(Looper.getMainLooper()).postDelayed({
            AnimationUtils.animateFadeIn(byText, 800)
        }, 500)

        // Handler to delay the transition to LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Apply exit animation before transitioning
            AnimationUtils.animateFadeIn(splashImage, 500)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 300)
        }, 2500) // 2.5 seconds delay to allow for animations
    }
} 