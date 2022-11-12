package com.example.notes.views

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.ActivityEditTaskBinding
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.models.Task
import com.example.notes.models.TaskSerializer
import com.example.notes.models.TasksDatabase
import com.example.notes.models.TasksRepository
import com.example.notes.viewModels.TasksViewModel
import com.example.notes.viewModels.TasksViewModelFactory
import com.google.android.material.snackbar.Snackbar

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
            val taskSerializable = intent.getSerializableExtra("Task") as TaskSerializer
            etTitleEdit.setText(taskSerializable.title)
            etDescriptionEdit.setText(taskSerializable.task)

            btnBack.setOnClickListener {
                taskSerializable.title = etTitleEdit.text.toString()
                taskSerializable.task = etDescriptionEdit.text.toString()
                viewModel.update(TaskSerializer.toTask(taskSerializable))
                finish()
            }

            btnDelete.setOnClickListener {
                val task = TaskSerializer.toTask(taskSerializable)
                viewModel.delete(task)
                Snackbar.make(binding.root, R.string.task_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo) {
                        viewModel.add(task)
                    }
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            if (event != DISMISS_EVENT_ACTION)
                                finish()
                        }
                    })
                    .show()
            }
        }
    }
}