package org.example.organizame.data.models

data class Column(
    val id: Long,
    val boardId: Long,
    val name: String,
    val position: Int,
    val tasks: List<Task> = emptyList()
)
