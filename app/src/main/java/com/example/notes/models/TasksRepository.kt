package com.example.notes.models

class TasksRepository(private val db: TasksDatabase) {
    suspend fun insert(task: Task) = db.getTasksDao().insert(task)

    suspend fun delete(task: Task) = db.getTasksDao().delete(task)

    suspend fun update(task: Task) = db.getTasksDao().update(task)

    fun getAllTasks() = db.getTasksDao().getAllTasks()

    fun getImportantTasks() = db.getTasksDao().getImportantTasks()

    fun getCompletedTasks() = db.getTasksDao().getCompletedTasks()
}