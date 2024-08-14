package com.example.reminderapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var requestNotificationPermissionLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize permission request launcher
        requestNotificationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, proceed with notifications
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permission denied, notify the user
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        TaskRepository.loadTasks(this) // Load tasks from SharedPreferences
        NotificationHelper.createNotificationChannel(this)

//        NotificationHelper.showNotification(this, "Test", "This is a test notification.")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskAdapter = TaskAdapter(this, TaskRepository.taskList)
        recyclerView.adapter = taskAdapter


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        taskAdapter.notifyDataSetChanged() // Refresh the list when returning from AddTaskActivity
    }

    override fun onPause() {
        super.onPause()
        TaskRepository.saveTasks(this) // Save tasks to SharedPreferences
    }

//    private fun checkAndRequestExactAlarmPermission(context: Context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            // Check if the app has the permission to schedule exact alarms
//            val isPermissionGranted = context.getSystemService(Context.ALARM_SERVICE)
//                ?.let { it as AlarmManager }
//                ?.canScheduleExactAlarms() ?: false
//
//            if (!isPermissionGranted) {
//                // Show a toast or UI message to inform the user
//                Toast.makeText(
//                    context,
//                    "Please grant 'Schedule Exact Alarms' permission in Settings.",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                // Open the settings screen where the user can grant the permission
//                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
//                context.startActivity(intent)
//            }
//        }
//    }

}

