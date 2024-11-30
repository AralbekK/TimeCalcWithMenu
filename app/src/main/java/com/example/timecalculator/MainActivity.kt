package com.example.timecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var timeInput1: EditText
    private lateinit var timeInput2: EditText
    private lateinit var subtractButton: Button
    private lateinit var addButton: Button
    private lateinit var resultText: TextView
    private lateinit var messageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        timeInput1 = findViewById(R.id.timeInput1)
        timeInput2 = findViewById(R.id.timeInput2)
        subtractButton = findViewById(R.id.subtractButton)
        addButton = findViewById(R.id.addButton)
        resultText = findViewById(R.id.resultText)
        messageText = findViewById(R.id.messageText)

        resultText.setTextColor(Color.BLACK)  // Установка черного цвета текста при запуске

        subtractButton.setOnClickListener {
            if (timeInput1.text.isNotEmpty() && timeInput2.text.isNotEmpty()) {
                val time1 = convertToSeconds(timeInput1.text.toString())
                val time2 = convertToSeconds(timeInput2.text.toString())
                val result = time1 - time2
                resultText.text = formatTime(result)
                resultText.setTextColor(Color.parseColor("#8B0000"))
                messageText.text = ""
                Toast.makeText(this, "Результат: ${formatTime(result)}", Toast.LENGTH_SHORT).show()
            } else {
                messageText.text = "Введите значения времени в оба поля"
            }
        }

        addButton.setOnClickListener {
            if (timeInput1.text.isNotEmpty() && timeInput2.text.isNotEmpty()) {
                val time1 = convertToSeconds(timeInput1.text.toString())
                val time2 = convertToSeconds(timeInput2.text.toString())
                val result = time1 + time2
                resultText.text = formatTime(result)
                resultText.setTextColor(Color.parseColor("#8B0000"))
                messageText.text = ""
                Toast.makeText(this, "Результат: ${formatTime(result)}", Toast.LENGTH_SHORT).show()
            } else {
                messageText.text = "Введите значения времени в оба поля"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear -> {
                timeInput1.text.clear()
                timeInput2.text.clear()
                resultText.text = ""
                messageText.text = ""
                resultText.setTextColor(Color.BLACK)
                Toast.makeText(this, "Данные очищены", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_exit -> {
                finish()
                Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show()
    }

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

    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return "${if (hours > 0) "${hours}h" else ""}${if (minutes > 0) "${minutes}m" else ""}${if (secs > 0) "${secs}s" else ""}".trim()
    }
}
