package com.example.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    fun addTask(task: Task) {
        MockDatabase.tasksList.value = getAllTasks().value?.plus(task) ?: listOf(task)
    }

    fun deleteTask(position: Int) {
        if (MockDatabase.tasksList.value == null) return
        MockDatabase.tasksList.value = getAllTasks().value!!.toMutableList().apply {
            removeAt(position)
        }.toList()
    }

    fun getAllTasks() = MockDatabase.tasksList
}