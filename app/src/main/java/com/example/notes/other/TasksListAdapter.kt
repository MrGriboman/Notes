package com.example.notes.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.models.Task
import com.example.notes.databinding.TaskItemBinding

class TasksListAdapter(var tasks: List<Task>, val clickListener: (Task) -> Unit) :
    RecyclerView.Adapter<TasksListAdapter.TasksListViewHolder>() {

    class TasksListViewHolder(
        val binding: TaskItemBinding,
        val clickAtPosition: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.recyclerItem.setOnClickListener { clickAtPosition(adapterPosition) }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TasksListViewHolder(binding) {clickListener(tasks[it])}
    }

    override fun onBindViewHolder(holder: TasksListViewHolder, position: Int) {
        holder.binding.apply {
            tvTaskTitle.text = tasks[position].title
            tvTaskDescription.text = tasks[position].task
            cbCompleted.isChecked = tasks[position].isCompleted
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}