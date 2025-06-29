package org.example.organizame.data.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Board(
    val id: Long,
    val name: String
)

data class Column(
    val id: Long,
    val boardId: Long,
    val name: String,
    val position: Int
)

data class Task @OptIn(ExperimentalTime::class) constructor(
    val id: Long,
    val columnId: Long,
    val title: String,
    val description: String?,
    val position: Int,
    val createdAt: Instant
)

data class ChecklistItem(
    val id: Long,
    val taskId: Long,
    val text: String,
    val isCompleted: Boolean,
    val position: Int
)

data class Comment @OptIn(ExperimentalTime::class) constructor(
    val id: Long,
    val taskId: Long,
    val text: String,
    val createdAt: Instant
)
