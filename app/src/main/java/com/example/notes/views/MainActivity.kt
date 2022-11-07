package com.example.notes.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.other.CreateTaskDialog
import com.example.notes.models.Task
import com.example.notes.other.TasksItemTouchHelper
import com.example.notes.other.TasksListAdapter
import com.example.notes.viewModels.TasksViewModel
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CreateTaskDialog.CreateTaskDialogInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        val tasksList = viewModel.getAllTasks().value ?: listOf()
        adapter = TasksListAdapter(tasksList)
        layoutManager = LinearLayoutManager(this)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it
            adapter.notifyItemChanged(0)
        }
        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)
        viewModel.getAllTasks().observe(this, tasksObserver)
        setContentView(binding.root)

        binding.apply {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = layoutManager
            rvTasks.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )

            fbtnAdd.setOnClickListener {
                addNewTask()
            }
        }
    }

    private fun addNewTask() {
        CreateTaskDialog().show(supportFragmentManager, "Add task")
    }

    override fun updateTasks(title: String, task: String) {
        viewModel.addTask(Task(title, task, false))
    }
}