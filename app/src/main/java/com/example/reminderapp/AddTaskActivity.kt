package com.example.reminderapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.widget.EditText
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var editTextDateTime: EditText
    private lateinit var btnSave: Button
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTextDateTime = findViewById(R.id.editTextDateTime)
        btnSave = findViewById(R.id.saveButton)

        editTextDateTime.setOnClickListener {
            showDateTimePicker()
        }

        btnSave.setOnClickListener {
            saveTask()
        }
    }

    private fun showDateTimePicker() {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            showTimePicker()
        }

        DatePickerDialog(this, dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker() {
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            updateDateTimeDisplay()
        }

        TimePickerDialog(this, timeListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true).show()
    }

    private fun updateDateTimeDisplay() {
        editTextDateTime.setText(dateFormat.format(calendar.time))
    }

    private fun saveTask() {
        // Save the task with the selected date and time
        val title = findViewById<EditText>(R.id.editTextTitle).text.toString()
        val description = findViewById<EditText>(R.id.editTextDescription).text.toString()
        val dueDate = calendar.time

        val task = Task(
            id = TaskRepository.taskList.size + 1, // Simple ID generation
            title = title,
            description = description,
            dueDate = dueDate
        )
//        scheduleNotification(this, title, description, calendar)

        TaskRepository.taskList.add(task)
        TaskRepository.saveTasks(this) // Save tasks to SharedPreferences
//        Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()

        NotificationHelper.showNotification(this, title, "A new Task has been successfully added")
        finish() // Go back to MainActivity
    }

//    private fun scheduleNotification(context: Context, title: String, message: String, triggerTime: Calendar) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, NotificationReceiver::class.java).apply {
//            putExtra("title", title)
//            putExtra("message", message)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, pendingIntent
//        )
//    }
}
