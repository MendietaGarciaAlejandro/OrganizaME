package org.example.organizame.data.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Board(
    val id: Long,
    val name: String
)

data class Task(
    val id: Long,
    val columnId: Long,
    val title: String,
    val description: String? = null,
    val position: Int,
    val createdAt: Instant = Clock.System.now()
)

data class Column(
    val id: Long,
    val boardId: Long,
    val name: String,
    val position: Int,
    val tasks: List<Task> = emptyList()
)

data class ChecklistItem(
    val id: Long,
    val taskId: Long,
    val text: String,
    val isCompleted: Boolean,
    val position: Int
)

data class Comment(
    val id: Long,
    val taskId: Long,
    val text: String,
    val createdAt: Instant = Clock.System.now()
)
