package com.nhlynn.number_to_words_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nhlynn.number_to_words_converter.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {
            if (binding.edtNumber.text.isNullOrEmpty()) {
                Toast.makeText(this, getString(R.string.enter_number), Toast.LENGTH_LONG).show()
            } else {
                try {
                    binding.tvResult.text = numberToWord(binding.edtNumber.text.toString().toInt())
                } catch (e: Exception) {
                    binding.tvResult.text = e.toString()
                }
            }
        }
    }

    private fun numberToWord(num: Int): String {
        // variable to hold string representation of number
        var number = num
        var words = ""
        val unitsArray = arrayOf(
            "zero", "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen"
        )
        val tensArray = arrayOf(
            "zero", "ten", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"
        )
        if (number == 0) {
            return "zero"
        }

        // add minus before conversion if the number is less than 0
        if (number < 0) {
            // convert the number to a string
            var numberStr = "" + number
            // remove minus before the number
            numberStr = numberStr.substring(1)
            // add minus before the number and convert the rest of number
            return "minus " + numberToWord(numberStr.toInt())
        }

        // check if number is divisible by 1 billion
        if (number / 1000000000 > 0) {
            words += numberToWord(number / 1000000000) + " billion "
            number %= 1000000000
        }

        // check if number is divisible by 1 million
        if (number / 1000000 > 0) {
            words += numberToWord(number / 1000000) + " million "
            number %= 1000000
        }

        // check if number is divisible by 1 thousand
        if (number / 1000 > 0) {
            words += numberToWord(number / 1000) + " thousand "
            number %= 1000
        }

        // check if number is divisible by 1 hundred
        if (number / 100 > 0) {
            words += numberToWord(number / 100) + " hundred "
            number %= 100
        }

        if (number > 0) {
            // check if number is within teens
            if (number < 20) {
                // fetch the appropriate value from unit array
                words += unitsArray[number]
            } else {
                // fetch the appropriate value from tens array
                words += tensArray[(number / 10)]
                if (number % 10 > 0) {
                    words += "-" + unitsArray[(number % 10)]
                }
            }
        }
        return words
    }
}