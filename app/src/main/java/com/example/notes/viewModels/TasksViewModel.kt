package com.example.notes.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.MockDatabase
import com.example.notes.models.Task
import com.example.notes.models.TasksRepository
import kotlinx.coroutines.launch

class TasksViewModel(application: Application, private val repository: TasksRepository) :
    AndroidViewModel(application) {

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun getAllTasks() = repository.getAllTasks()
}