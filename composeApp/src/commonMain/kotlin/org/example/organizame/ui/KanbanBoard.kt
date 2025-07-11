package org.example.organizame.ui

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.organizame.data.models.Column
import org.example.organizame.data.models.Task

data class DragInfo(val taskId: Long, val fromColumn: Long)

@Composable
fun KanbanBoard(viewModel: KanbanViewModel, modifier: Modifier = Modifier) {
    var dragInfo by remember { mutableStateOf<DragInfo?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = modifier) {
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.columns, key = { it.id }) { col ->
                KanbanColumn(
                    column = col,
                    dragInfo = dragInfo,
                    onDragStart = { id -> dragInfo = DragInfo(id, col.id) },
                    onDrag = { offset -> dragOffset += offset },
                    onDragEnd = { dragInfo = null; dragOffset = Offset.Zero },
                    onDrop = { targetId ->
                        dragInfo?.let {
                            viewModel.moveTask(it.taskId, it.fromColumn, targetId)
                        }
                        dragInfo = null; dragOffset = Offset.Zero
                    },
                    modifier = Modifier.width(300.dp)
                )
            }
        }
    }
}

@Composable
fun KanbanColumn(
    column: Column,
    dragInfo: DragInfo?,
    onDragStart: (Long) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDrop: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Card(modifier = modifier.fillMaxHeight(0.95f)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(column.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(column.tasks, key = { it.id }) { task ->
                    val dragging = dragInfo?.taskId == task.id
                    TaskCard(
                        task = task,
                        modifier = Modifier
                            .graphicsLayer { alpha = if (dragging) 0.5f else 1f }
                            .zIndex(if (dragging) 1f else 0f)
                            .pointerInput(task.id) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = { onDragStart(task.id) },
                                    onDragEnd = { onDragEnd(); onDrop(column.id) },
                                    onDrag = { change, offset ->
                                        change.consume()
                                        onDrag(offset)
                                        // auto-scroll
                                        scope.launch {
                                            if (offset.y < 0) listState.scrollBy(-10f)
                                            else if (offset.y > 0) listState.scrollBy(10f)
                                        }
                                    }
                                )
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
        Column(Modifier.padding(8.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleSmall)
            task.description?.let {
                if (it.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(it, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                }
            }
        }
    }
}
