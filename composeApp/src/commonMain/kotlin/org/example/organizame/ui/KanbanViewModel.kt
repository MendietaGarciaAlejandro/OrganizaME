package org.example.organizame.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.organizame.data.models.Column
import org.example.organizame.data.models.Task

class KanbanViewModel {
    var columns by mutableStateOf<List<Column>>(emptyList())
        private set

    fun moveTask(taskId: Long, sourceColumnId: Long, targetColumnId: Long) {
        if (sourceColumnId == targetColumnId) return

        val sourceColumn = columns.find { it.id == sourceColumnId }
        val taskToMove = sourceColumn?.tasks?.find { it.id == taskId }

        if (taskToMove != null) {
            val targetColumn = columns.find { it.id == targetColumnId }
            val targetTasks = targetColumn?.tasks.orEmpty()
            val newPosition = targetTasks.maxOfOrNull { it.position }?.plus(1) ?: 0

            val updatedTask = taskToMove.copy(
                columnId = targetColumnId,
                position = newPosition
            )

            columns = columns.map { column ->
                when (column.id) {
                    sourceColumnId -> column.copy(
                        tasks = column.tasks.filterNot { it.id == taskId }
                    )
                    targetColumnId -> column.copy(
                        tasks = column.tasks + updatedTask
                    )
                    else -> column
                }
            }
        }
    }

    fun setColumns(newColumns: List<Column>) {
        columns = newColumns
    }
}
