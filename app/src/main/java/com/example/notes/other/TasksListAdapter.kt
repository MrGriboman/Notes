package com.example.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Models.Task
import com.example.notes.databinding.TaskItemBinding

class TasksListAdapter(var tasks: List<Task>, val viewModel: TasksViewModel)
    : RecyclerView.Adapter<TasksListAdapter.TasksListViewHolder>() {

    inner class TasksListViewHolder(val binding: TaskItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TasksListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksListViewHolder, position: Int) {
        holder.binding.apply {
            tvTaskTitle.text = tasks[position].title
            tvTaskDescription.text = tasks[position].task
            cbCompleted.isChecked = tasks[position].competed
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteTask(viewHolder.adapterPosition)
            this@TasksListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
        }
    }

}