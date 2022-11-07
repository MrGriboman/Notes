package com.example.notes

import androidx.recyclerview.widget.ItemTouchHelper

class TasksItemTouchHelper(
    private val viewModel: TasksViewModel,
    private val adapter: TasksListAdapter
    )
    : ItemTouchHelper(TasksItemTouchHelperCallback(viewModel, adapter))
{

}