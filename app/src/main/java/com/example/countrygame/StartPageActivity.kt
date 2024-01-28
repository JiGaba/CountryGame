package com.example.countrygame

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        viewModel.showDialog.observe(this, Observer { d ->
            // Zde provedete akce při změně hodnoty proměnné
            if(d) showAlertDialog()
            //viewModel.setShowDialog(false)
            /*
            if (newValue) {
                // Akce pro případ, kdy je proměnná true
                Log.d("YourActivity", "Proměnná je nastavena na true")
            } else {
                // Akce pro případ, kdy je proměnná false
                Log.d("YourActivity", "Proměnná je nastavena na false")
            }*/
        })
/*
        binding.buttonStart.setOnClickListener {
            // Zavolání metody ve ViewModelu při kliknutí na tlačítko

            Log.v("OK?", "")
            // Zobrazení alertu
            showAlertDialog()
            viewModel.onButtonStartClick()
        }
*/
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

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Upozornění")
        alertDialogBuilder.setMessage("Pro pokračování musíte vybrat dva a více kontinentů.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            // Kód, který se provede po stisknutí tlačítka OK
            viewModel.setShowDialog(false)
            dialog.dismiss() // Zavře dialog
        }

        // Volání metody pro zobrazení alertu
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}