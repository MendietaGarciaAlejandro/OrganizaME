package org.example.organizame.data.models

data class Task(
    val id: Long,
    val columnId: Long,
    val title: String,
    val description: String,
    val position: Int,
    val createdAt: Long = System.currentTimeMillis()
)
