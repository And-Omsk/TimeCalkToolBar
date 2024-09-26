package com.example.timecalktoolbar

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textTV:TextView
    private lateinit var editText1:EditText
    private lateinit var editText2:EditText
    private lateinit var buttonPlus:Button
    private lateinit var buttonMinus:Button
    private lateinit var textView3:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editText1=findViewById(R.id.editText1)
        textTV=findViewById(R.id.textTV)
        editText2=findViewById(R.id.editText2)
        buttonPlus=findViewById(R.id.buttonPlus)
        buttonMinus=findViewById(R.id.buttonMinus)
        textView3=findViewById(R.id.textView3)

        onButtonClickPlus(buttonPlus)
        onButtonClickMinus(buttonMinus)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onButtonClickPlus(view: View){
        val time1=editText1.text
        val time2=editText2.text
        val sumResult = addTime(time1.toString(), time2.toString())

        textView3.text=sumResult

    }

    fun onButtonClickMinus(view: View){
        val time1=editText1.text
        val time2=editText2.text
        val subtractResult = subtractTime(time1.toString(), time2.toString())

        textView3.text=subtractResult

    }
    private fun timeToSeconds(timeString: String): Int {
        val regex = Regex("(\\d+)([hms])")
        val matches = regex.findAll(timeString)

        val timePairs = matches.map {
            val (number, unit) = it.destructured
            Pair(number.toInt(), unit)
        }.toList()

        val totalSeconds = timePairs.map { pair ->
            val (number, unit) = pair
            when (unit) {
                "h" -> number * 3600 // 1 час = 3600 секунд
                "m" -> number * 60   // 1 минута = 60 секунд
                "s" -> number
                else -> 0 // Возвращаем 0 в случае некорректной единицы времени
            }
        }.sum()

        return totalSeconds
    }

    private fun secondsToTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        return "${if (hours > 0) "$hours h " else ""}${if (minutes > 0) "$minutes m " else ""}${if (remainingSeconds > 0) "$remainingSeconds s" else ""}"
    }

    private fun addTime(time1: String, time2: String): String {
        val totalSeconds = timeToSeconds(time1) + timeToSeconds(time2)
        return secondsToTime(totalSeconds)
    }

    private fun subtractTime(time1: String, time2: String): String {
        val totalSeconds = timeToSeconds(time1) - timeToSeconds(time2)
        if (totalSeconds < 0) return "Ошибка!!Разность отрицательная!!"
        return secondsToTime(totalSeconds)
    }
}