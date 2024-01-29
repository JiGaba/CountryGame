package com.example.countrygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import java.util.logging.Handler
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // 2 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Počká na SPLASH_DELAY a poté spustí hlavní aktivitu
            val intent = Intent(this@SplashActivity, StartPageActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}