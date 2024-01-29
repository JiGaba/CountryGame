package com.example.countrygame

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.countrygame.databinding.ActivityCountryGameBinding
import com.example.countrygame.domain.CountryData
import com.example.countrygame.domain.Regions
import kotlin.random.Random

class CountryGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryGameBinding
    private lateinit var viewModel: CountryGameViewModel
    private lateinit var _qameData: List<CountryData>
    private lateinit var _actualGameData: CountryData
    private var  _score: Int = 0
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
                val selectedRegion: String = Regions.getRegionList.get(viewModel.responseNumber)

                if(selectedRegion.equals(_actualGameData.region, ignoreCase = true)){
                    val myRadioButton = getRadioButtonByIndex(viewModel.responseNumber)
                    setRadioButtonTextSucces(myRadioButton)
                    _score++
                }else{
                    var succesIndex: Int = 0

                    // Vyhledej správnou odpověď
                    for ((i, value) in Regions.getRegionList.withIndex()) {
                        if (value.equals(_actualGameData.region, ignoreCase = true)) {
                            succesIndex = i
                            break
                        }
                    }
                    val myRadioButtonSucces = getRadioButtonByIndex(succesIndex)
                    val myRadioButtonFalse = getRadioButtonByIndex(viewModel.responseNumber)
                    setRadioButtonTextSucces(myRadioButtonSucces)
                    setRadioButtonTextFalse(myRadioButtonFalse)
                    _score--
                }
                viewModel.setScore("Score: " + _score.toString())
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
            }else{
                for (i in 0 until radioGroup.childCount) {
                    val radioButton = radioGroup.getChildAt(i) as? RadioButton
                    radioButton?.isEnabled = true
                }
            }
        })
        // Nastav výchozí hodnoty
        viewModel.doDefault.observe(this, Observer { d ->
            if(d){
                Log.v("Nastavení výchozích hodnot", "")

                val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
                radioGroup.clearCheck()

                for (i in 0 until radioGroup.childCount) {
                    val radioButton = radioGroup.getChildAt(i) as? RadioButton
                    radioButton?.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                    val text = radioButton?.text.toString()

                    // Create a SpannableString to apply styles
                    val spannable = SpannableString(text)
                    // Apply the bold style to the entire text
                    spannable.setSpan(StyleSpan(Typeface.NORMAL), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    // Set the modified text to the RadioButton
                    radioButton?.text = spannable
                }

                getRandomCountry()
                viewModel.setLock(false)
                viewModel.setDoDefault(false)
            }
        })

        // zpět do setting
        viewModel.backToSetting.observe(this, Observer { d ->
            if(d){
                val intent = Intent(this, StartPageActivity::class.java)
                startActivity(intent)
                viewModel.setBackToSetting(false)
            }
        })
    }

    fun getRadioButtonByIndex(index: Int) : RadioButton{
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        when(index){
            0 -> {
                return findViewById(R.id.radioButtonAfrica)
            }
            1 -> {
                return findViewById(R.id.radioButtonAsia)
            }
            2 -> {
                return findViewById(R.id.radioButtonAntarctic)
            }
            3 -> {
                return findViewById(R.id.radioButtonEurope)
            }
            4 -> {
                return findViewById(R.id.radioButtonOceania)
            }
            5 -> {
                return findViewById(R.id.radioButtonSouthAmerica)
            }
            6 -> {
                return findViewById(R.id.radioButtonCentralAmerica)
            }
            7 -> {
                return findViewById(R.id.radioButtonNorthAmerica)
            }
        }
        throw NullPointerException()
    }


    fun setRadioButtonTextSucces(myRadioButton: RadioButton){
        myRadioButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        val text = myRadioButton.text.toString()

        // Create a SpannableString to apply styles
        val spannable = SpannableString(text)
        // Apply the bold style to the entire text
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        // Set the modified text to the RadioButton
        myRadioButton.text = spannable
    }

    fun setRadioButtonTextFalse(myRadioButton: RadioButton){
        myRadioButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        val text = myRadioButton.text.toString()

        // Create a SpannableString to apply styles
        val spannable = SpannableString(text)
        // Apply the bold style to the entire text
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        // Set the modified text to the RadioButton
        myRadioButton.text = spannable
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