package com.example.notes.other

//import com.example.notes.models.Task
//import com.example.notes.viewModels.TasksViewModel
//
//class TaskUpdater private constructor(private val viewModel: TasksViewModel) {
//
//    companion object {
//        @Volatile
//        private lateinit var instance: TaskUpdater
//
//        @Synchronized
//        fun getInstance(viewModel: TasksViewModel): TaskUpdater {
//            if (!::instance.isInitialized) {
//                instance = TaskUpdater(viewModel)
//            }
//            return instance
//        }
//    }
//
//    fun update(title: String, task: String, isCompleted: Boolean, ID: Int?) {
//        val task = Task(title, task, isCompleted, ID)
//        viewModel.update(task)
//    }
//}