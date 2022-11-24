package com.example.notes.views

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.ActivityEditTaskBinding
import com.example.notes.models.TaskSerializer
import com.example.notes.models.TasksDatabase
import com.example.notes.models.TasksRepository
import com.example.notes.viewModels.TasksViewModel
import com.example.notes.viewModels.TasksViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    private lateinit var viewModel: TasksViewModel
    private lateinit var taskSerializable: TaskSerializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)

        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]
        setContentView(binding.root)

        binding.apply {
            taskSerializable = intent.getSerializableExtra("Task") as TaskSerializer
            etTitleEdit.setText(taskSerializable.title)
            etDescriptionEdit.setText(taskSerializable.task)
            tvDate.text = taskSerializable.date

            btnBack.setOnClickListener {onBackPressed()}

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

            tvDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)


                val dpd = DatePickerDialog(
                    this@EditTaskActivity,
                    { _, yearPicked, monthOfYear, dayOfMonth ->
                        calendar.set(Calendar.YEAR, yearPicked)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date = DateFormat.getDateInstance().format(calendar.time)
                        tvDate.text = date
                    },
                    year,
                    month,
                    day
                )

                dpd.show()
            }
        }
    }

    override fun onBackPressed() {
        binding.apply {
            taskSerializable.title = etTitleEdit.text.toString()
            taskSerializable.task = etDescriptionEdit.text.toString()
            taskSerializable.date = tvDate.text.toString()
        }
        viewModel.update(TaskSerializer.toTask(taskSerializable))
        super.onBackPressed()
    }
}