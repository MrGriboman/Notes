package com.example.notes.other

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.viewModels.TasksViewModel
import com.google.android.material.snackbar.Snackbar

class TasksItemTouchHelperCallback(
    private val viewModel: TasksViewModel,
    private val adapter: TasksListAdapter,
    private val view: View
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val task = adapter.tasks[viewHolder.adapterPosition]
        viewModel.delete(task)
        Snackbar.make(view, R.string.task_deleted, Snackbar.LENGTH_LONG)
            .setAction(R.string.undo) {viewModel.add(task)}
            .show()
    }
}