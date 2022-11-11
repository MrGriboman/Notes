package com.example.notes.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.ActivityEditTaskBinding
import com.example.notes.models.Task
import com.example.notes.models.TaskSerializable
import com.example.notes.models.TasksDatabase
import com.example.notes.models.TasksRepository
import com.example.notes.viewModels.TasksViewModel
import com.example.notes.viewModels.TasksViewModelFactory

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]
        setContentView(binding.root)

        binding.apply {
            val taskSerializable = intent.getSerializableExtra("Task") as TaskSerializable
            etTitleEdit.setText(taskSerializable.title)
            etDescriptionEdit.setText(taskSerializable.task)
            btnBack.setOnClickListener {
                val editedTask = Task(
                    etTitleEdit.text.toString(),
                    etDescriptionEdit.text.toString(),
                    taskSerializable.isCompleted,
                    taskSerializable.ID
                )
                viewModel.update(editedTask)
                finish()
            }
        }
    }
}