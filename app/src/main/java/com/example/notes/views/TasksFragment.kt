package com.example.notes.views

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.databinding.FragmentTasksBinding
import com.example.notes.models.Task
import com.example.notes.models.TaskSerializer
import com.example.notes.models.TasksDatabase
import com.example.notes.models.TasksRepository
import com.example.notes.other.CreateTaskDialog
import com.example.notes.other.TasksItemTouchHelper
import com.example.notes.other.TasksListAdapter
import com.example.notes.viewModels.TasksViewModel
import com.example.notes.viewModels.TasksViewModelFactory

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel
    private lateinit var activityContext: Context
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        val database = TasksDatabase.getInstance(activityContext)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        adapter =
            TasksListAdapter(viewModel.getAllTasks().value?.reversed() ?: listOf(), viewModel) {
                Intent(activityContext, EditTaskActivity::class.java).also { intent ->
                    intent.putExtra("Task", TaskSerializer.fromTask(it))
                    startActivity(intent)
                }
            }
        layoutManager = LinearLayoutManager(activityContext)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it.reversed()
            adapter.notifyDataSetChanged()
        }
        viewModel.getAllTasks().observe(viewLifecycleOwner, tasksObserver)

        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter, binding.root)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)
        binding.apply {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = layoutManager
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        application = (context as Activity).application
    }
}
