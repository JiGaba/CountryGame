package com.example.countrygame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.countrygame.databinding.ActivityCountryGameBinding

class StartPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        /*
        binding = ActivityCountryGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val app = application as MyApplication
        viewModel = ViewModelProvider(this, CountryViewModelFactory(app.repository))
            .get(CountryGameViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        var regions : MutableList<String> = mutableListOf()
        regions.add("africa")
        regions.add("europe")
        viewModel.getSubjectInfo(regions, "300", true)
        */
        //val radioButtonToRemove: View = findViewById(R.id.radioButtonCentralAmerica)
        //binding.radioGroup.removeView(radioButtonToRemove)
    }
}