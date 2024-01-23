package com.example.countrygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.countrygame.databinding.ActivityCountryGameBinding

class CountryGameActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCountryGameBinding
    private  lateinit var viewModel: CountryGameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_game)

        binding = ActivityCountryGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.txtLabel.text = "Test success"

        viewModel = ViewModelProvider(this)
            .get(CountryGameViewModel::class.java)
    }
}