package com.example.notes.other

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notes.viewModels.TasksViewModel

class TasksItemTouchHelper(
    viewModel: TasksViewModel,
    adapter: TasksListAdapter,
    view: View
) : ItemTouchHelper(TasksItemTouchHelperCallback(viewModel, adapter, view))