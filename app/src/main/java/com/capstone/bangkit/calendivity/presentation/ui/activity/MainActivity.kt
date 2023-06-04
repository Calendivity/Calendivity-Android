package com.capstone.bangkit.calendivity.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.bangkit.calendivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // splash screen
        installSplashScreen()

        super.onCreate(savedInstanceState)
        // define view binding

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //TODO : Implement Consenst Screen using google api
    }
}