package com.example.notes.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.databinding.ActivityEditTaskBinding
import com.example.notes.models.TaskSerializable

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val taskSerializable = intent.getSerializableExtra("Task") as TaskSerializable
            etTitleEdit.setText(taskSerializable.title)
            etDescriptionEdit.setText(taskSerializable.task)
            btnBack.setOnClickListener { finish() }
        }
    }
}