package com.example.countrygame

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.example.countrygame.databinding.ActivityCountryGameBinding

class CountryGameActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCountryGameBinding
    private  lateinit var viewModel: CountryGameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_game)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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

        //val radioButtonToRemove: View = findViewById(R.id.radioButtonCentralAmerica)
        //binding.radioGroup.removeView(radioButtonToRemove)
        /*
        val myButton: Button = findViewById(R.id.buttonSubmit)
        myButton.setOnClickListener {
            // Call the method when the button is clicked
            test()
        }

         */
    }

    fun test(){
        Log.v("jsdjsd", "sasfds")
    }
}