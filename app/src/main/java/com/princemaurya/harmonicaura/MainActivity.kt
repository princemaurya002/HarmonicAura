package com.princemaurya.harmonicaura

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.navigation.NavController
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure window insets
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        try {
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            
            // Create a color state list for the icons
            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            )
            val colors = intArrayOf(
                ContextCompat.getColor(this, R.color.primary_blue),
                Color.GRAY
            )
            val colorStateList = ColorStateList(states, colors)
            
            // Apply the color state list to the navigation items
            bottomNavigationView.itemIconTintList = colorStateList
            bottomNavigationView.itemTextColor = colorStateList

            // Navigation setup
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            // Set up the bottom navigation with the nav controller
            bottomNavigationView.setupWithNavController(navController)

            // Add navigation listener for debugging
            navController.addOnDestinationChangedListener { _, destination, _ ->
                Log.d("Navigation", "Navigated to ${destination.label}")
            }

            // Ensure bottom navigation is visible
            bottomNavigationView.visibility = android.view.View.VISIBLE
        } catch (e: Exception) {
            Log.e("Navigation", "Error setting up navigation", e)
        }
    }
}