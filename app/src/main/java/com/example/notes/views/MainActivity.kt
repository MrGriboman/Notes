package com.example.notes.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
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
    private lateinit var viewModel: TasksViewModel
    private lateinit var allTasksFragment: TasksFragment
    private lateinit var importantTasksFragment: ImportantTasksFragment
    private lateinit var completedTasksFragment: CompletedTasksFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allTasksFragment = TasksFragment()
        importantTasksFragment = ImportantTasksFragment()
        completedTasksFragment = CompletedTasksFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frgTasks, allTasksFragment)
            commit()
        }

        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        binding.apply {
            fbtnAdd.setOnClickListener {
                CreateTaskDialog().show(supportFragmentManager, "Add task")
            }
            burgerMenu.setOnClickListener { showPopup(burgerMenu) }
        }
    }

    override fun addTask(title: String, task: String, date: String) {
        val newTask = Task(title, task, isCompleted = false, isImportant = false, date)
        viewModel.add(newTask)
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        popup.inflate(R.menu.burger_menu)
        popup.setOnMenuItemClickListener {menuItemClickListener(it)}
        popup.show()
    }

    private fun menuItemClickListener(it: MenuItem): Boolean {
        when(it.itemId) {
            R.id.miAllTasks -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frgTasks, allTasksFragment)
                    commit()
                }
                return true
            }
            R.id.miImportantTasks -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frgTasks, importantTasksFragment)
                    commit()
                }
                return true
            }
            R.id.miCompletedTasks -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frgTasks, completedTasksFragment)
                    commit()
                }
                return true
            }
            else -> return false
        }
    }

}