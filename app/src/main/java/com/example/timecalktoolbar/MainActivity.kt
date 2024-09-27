package com.example.timecalktoolbar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editText1:EditText
    private lateinit var editText2:EditText
    private lateinit var buttonPlus:Button
    private lateinit var buttonMinus:Button
    private lateinit var textView3:TextView
    private lateinit var toolbarMain: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        toolbarMain=findViewById(R.id.toolbarMain)
        toolbarMain.title="Калькулятор времени"
        toolbarMain.subtitle="ver 1.0"
        toolbarMain.setLogo(R.drawable.baseline_calculate_24)
        editText1=findViewById(R.id.editText1)
        editText2=findViewById(R.id.editText2)
        buttonPlus=findViewById(R.id.buttonPlus)
        buttonMinus=findViewById(R.id.buttonMinus)
        textView3=findViewById(R.id.textView3)
        setSupportActionBar(toolbarMain)
        onButtonClickPlus(buttonPlus)
        onButtonClickMinus(buttonMinus)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
         menuInflater.inflate(R.menu.menu_main,menu)
         return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.resetMenuMain -> {
                editText1.text.clear()
                editText2.text.clear()
                textView3.text=""
                Toast.makeText(
                    applicationContext,
                    "Данные очищены",
                    Toast.LENGTH_LONG
                ).show()
            }
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    "Работа завершена.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
            return super.onOptionsItemSelected(item)

    }



    @SuppressLint("ResourceAsColor")
    fun onButtonClickPlus(view: View){
        val time1=editText1.text
        val time2=editText2.text
        val sumResult = addTime(time1.toString(), time2.toString())
        Toast.makeText(
            applicationContext,
            "Результат $sumResult.",
            Toast.LENGTH_LONG
        ).show()
        
        textView3.text=sumResult


    }

    @SuppressLint("ResourceAsColor")
    fun onButtonClickMinus(view: View){
        val time1=editText1.text
        val time2=editText2.text
        val subtractResult = subtractTime(time1.toString(), time2.toString())
        Toast.makeText(
            applicationContext,
            "Результат $subtractResult.",
            Toast.LENGTH_LONG
        ).show()
        textView3.setTextColor(Color.parseColor("#8B0000"))
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