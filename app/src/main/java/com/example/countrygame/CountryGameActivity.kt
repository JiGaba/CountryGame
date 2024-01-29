package com.example.countrygame

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.countrygame.databinding.ActivityCountryGameBinding
import com.example.countrygame.domain.CountryData
import com.example.countrygame.domain.Regions
import kotlin.random.Random

class CountryGameActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCountryGameBinding
    private  lateinit var viewModel: CountryGameViewModel
    private lateinit var _qameData: List<CountryData>
    private  lateinit var _actualGameData: CountryData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_game)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val setupData = loadIntentData()

        binding = ActivityCountryGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val app = application as MyApplication
        viewModel = ViewModelProvider(this, CountryViewModelFactory(app.repository))
            .get(CountryGameViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        removeUnusedRadio(setupData)
        viewModel.getSubjectInfo(setupData, "300", true)

        // Začátek hry - načtení požadovaných dat dle setupMnožiny
        viewModel.start.observe(this, Observer { d ->
            if(d){
                val countries = viewModel.countryInfoValue.value?.countries
                if(countries != null){
                    val filterValues = setupData

                    _qameData = countries.filter { obj ->
                        filterValues.any { filterValue -> obj.region.equals(filterValue, ignoreCase = true) }
                    }
                    getRandomCountry()
                }
            }
        })

        // Vybrána odpověď
        viewModel.response.observe(this, Observer { d ->
            if(d){
                /*
                val countries = viewModel.countryInfoValue.value?.countries
                if(countries != null){
                    val filterValues = setupData

                    _qameData = countries.filter { obj ->
                        filterValues.any { filterValue -> obj.region.equals(filterValue, ignoreCase = true) }
                    }
                    getRandomCountry()
                }
                */
                //viewModel.
                Log.v("response number ", viewModel.responseNumber.toString())
            }
        })

        // Uzamčení
        viewModel.lock.observe(this, Observer { l ->
            val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

            if(l){
                for (i in 0 until radioGroup.childCount) {
                    val radioButton = radioGroup.getChildAt(i) as? RadioButton
                    radioButton?.isEnabled = false
                }
                /*
                val countries = viewModel.countryInfoValue.value?.countries
                if(countries != null){
                    val filterValues = setupData

                    _qameData = countries.filter { obj ->
                        filterValues.any { filterValue -> obj.region.equals(filterValue, ignoreCase = true) }
                    }
                    getRandomCountry()
                }
                */
                //viewModel.
                //Log.v("lock ", l.toString())
            }else{
                for (i in 0 until radioGroup.childCount) {
                    val radioButton = radioGroup.getChildAt(i) as? RadioButton
                    radioButton?.isEnabled = true
                }
                //Log.v("lock ", l.toString())
            }
        })
    }

    fun getRandomCountry(){
        _actualGameData = _qameData.get(Random.nextInt(_qameData.count()))
        viewModel.setActualCountry(_actualGameData.country)
        Log.v("random country: ", _actualGameData.country)
        Log.v("random region: ", _actualGameData.region)
    }

    fun loadIntentData() : List<String>{
        // Příjem pole z Intentu
        val receivedStringArray = intent.getStringArrayExtra("setupData")

        // Zkontrolujte, zda bylo pole úspěšně přijato
        if (receivedStringArray != null) {
            Log.v("CountryGameActivity", "Příjem v pořádku")
        } else {
            Log.e("CountryGameActivity", "Chyba při přijímání pole")
        }

        return receivedStringArray?.filterNotNull() ?: emptyList()
    }

    fun removeUnusedRadio(setupData: List<String>){
        //val isStringInList: Boolean = setupData.any { it.equals("dd", ignoreCase = true) }
        Regions.getRegionList.forEach{r ->
            if(!setupData.any { it.equals(r, ignoreCase = true) }){
                when(r){
                    "africa" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonAfrica)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "asia" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonAsia)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "antarctic" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonAntarctic)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "europe" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonEurope)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "oceania" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonOceania)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "south america" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonSouthAmerica)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "central america" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonCentralAmerica)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                    "north america" -> {
                        val radioButtonToRemove: View = findViewById(R.id.radioButtonNorthAmerica)
                        binding.radioGroup.removeView(radioButtonToRemove)
                    }
                }
            }
        }

    }
}