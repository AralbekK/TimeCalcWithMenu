package com.example.timecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // кнопки
        val timeInput1: EditText = findViewById(R.id.timeInput1)
        val timeInput2: EditText = findViewById(R.id.timeInput2)
        val subtractButton: Button = findViewById(R.id.subtractButton)
        val addButton: Button = findViewById(R.id.addButton)
        val resultText: TextView = findViewById(R.id.resultText)
        val messageText: TextView = findViewById(R.id.messageText)

        // вычитание с проверкой на пустоту
        subtractButton.setOnClickListener {
            if (timeInput1.text.isNotEmpty() && timeInput2.text.isNotEmpty()) {
                val time1 = convertToSeconds(timeInput1.text.toString())
                val time2 = convertToSeconds(timeInput2.text.toString())
                val result = time1 - time2
                resultText.text = formatTime(result)
                messageText.text = ""
            } else {
                messageText.text = "Введите значения времени в оба поля"
            }
        }

        // сложение с проверкой на пустоту
        addButton.setOnClickListener {
            if (timeInput1.text.isNotEmpty() && timeInput2.text.isNotEmpty()) {
                val time1 = convertToSeconds(timeInput1.text.toString())
                val time2 = convertToSeconds(timeInput2.text.toString())
                val result = time1 + time2
                resultText.text = formatTime(result)
                messageText.text = ""
            } else {
                messageText.text = "Введите значения времени в оба поля"
            }
        }
    }

    // обработка по условию задачи с конвертацией в секудны
    private fun convertToSeconds(time: String): Int {
        var seconds = 0
        val regex = Regex("(\\d+h)?(\\d+m)?(\\d+s)?")
        val matchResult = regex.matchEntire(time.trim())
        matchResult?.destructured?.toList()?.forEach {
            if (it.endsWith("h")) seconds += it.dropLast(1).toInt() * 3600
            if (it.endsWith("m")) seconds += it.dropLast(1).toInt() * 60
            if (it.endsWith("s")) seconds += it.dropLast(1).toInt()
        }
        return seconds
    }

    // обратная конвертацию секунд
    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return "${if (hours > 0) "${hours}h" else ""}${if (minutes > 0) "${minutes}m" else ""}${if (secs > 0) "${secs}s" else ""}".trim()
    }
}
