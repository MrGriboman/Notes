package com.example.notes.other

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notes.viewModels.TasksViewModel

class TasksItemTouchHelper(
    viewModel: TasksViewModel,
    adapter: TasksListAdapter
) : ItemTouchHelper(TasksItemTouchHelperCallback(viewModel, adapter))