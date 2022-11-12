package com.example.notes.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.other.CreateTaskDialog
import com.example.notes.models.Task
import com.example.notes.other.TasksItemTouchHelper
import com.example.notes.other.TasksListAdapter
import com.example.notes.viewModels.TasksViewModel
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.models.TaskSerializer
import com.example.notes.models.TasksDatabase
import com.example.notes.models.TasksRepository
import com.example.notes.viewModels.TasksViewModelFactory

class MainActivity : AppCompatActivity(), CreateTaskDialog.CreateTaskDialogInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        adapter =
            TasksListAdapter(viewModel.getAllTasks().value?.reversed() ?: listOf(), viewModel) {
                Intent(this, EditTaskActivity::class.java).also { intent ->
                    intent.putExtra("Task", TaskSerializer.fromTask(it))
                    startActivity(intent)
                }
            }
        layoutManager = LinearLayoutManager(this)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it.reversed()
            adapter.notifyDataSetChanged()
        }
        viewModel.getAllTasks().observe(this, tasksObserver)

        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter, binding.root)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)
        setContentView(binding.root)

        binding.apply {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = layoutManager

            fbtnAdd.setOnClickListener {
                CreateTaskDialog().show(supportFragmentManager, "Add task")
            }
        }
    }

    override fun addTask(title: String, task: String) {
        val newTask = Task(title, task, false)
        viewModel.add(newTask)
    }
}