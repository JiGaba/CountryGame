package com.example.countrygame

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.countrygame.databinding.ActivityStartPageBinding

class StartPageActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityStartPageBinding
    private lateinit var viewModel: StartPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityStartPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(StartPageViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //val myButton: Button = findViewById(R.id.buttonStart)
        /*
        myButton.setOnClickListener {
            // Call the method when the button is clicked
            startGame()
        }

         */
        /*

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

    fun startGame(){
        Log.v("Test", "testdata")
        //viewModel.
    }
}