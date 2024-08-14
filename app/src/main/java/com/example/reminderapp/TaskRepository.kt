package com.example.reminderapp

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskRepository {
    private const val PREFS_NAME = "task_prefs"
    private const val TASK_KEY = "tasks"

    private val gson = Gson()
    var taskList: MutableList<Task> = mutableListOf()

    fun loadTasks(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val tasksJson = prefs.getString(TASK_KEY, null)

        if (!tasksJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            taskList = gson.fromJson(tasksJson, type)
        }
    }

    fun saveTasks(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val tasksJson = gson.toJson(taskList)
        editor.putString(TASK_KEY, tasksJson)
        editor.apply()
    }

}
