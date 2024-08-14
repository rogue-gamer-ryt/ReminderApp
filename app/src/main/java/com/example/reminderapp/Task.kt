package com.example.reminderapp

import java.util.Date


data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: Date
)

