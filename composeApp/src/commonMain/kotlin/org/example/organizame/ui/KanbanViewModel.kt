package org.example.organizame.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.organizame.data.models.Column

class KanbanViewModel {
    var columns by mutableStateOf<List<Column>>(emptyList())
        private set

    fun initializeColumns(initial: List<Column>) {
        columns = initial
    }

    fun moveTask(taskId: Long, sourceColumnId: Long, targetColumnId: Long) {
        if (sourceColumnId == targetColumnId) return

        val updatedColumns = columns.toMutableList()
        val sourceIdx = updatedColumns.indexOfFirst { it.id == sourceColumnId }
        val targetIdx = updatedColumns.indexOfFirst { it.id == targetColumnId }
        if (sourceIdx == -1 || targetIdx == -1) return

        val sourceCol = updatedColumns[sourceIdx]
        val targetCol = updatedColumns[targetIdx]
        val task = sourceCol.tasks.find { it.id == taskId } ?: return

        // Reubica tarea y asigna nueva posición al final
        val maxPos = targetCol.tasks.maxOfOrNull { it.position } ?: -1
        val moved = task.copy(
            columnId = targetColumnId,
            position = maxPos + 1,
            createdAt = task.createdAt  // conserva la fecha original
        )

        updatedColumns[sourceIdx] = sourceCol.copy(
            tasks = sourceCol.tasks.filterNot { it.id == taskId }
        )
        updatedColumns[targetIdx] = targetCol.copy(
            tasks = targetCol.tasks + moved
        )

        columns = updatedColumns
    }
}
