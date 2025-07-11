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

data class DragInfo(
    val taskId: Long,
    val sourceColumnId: Long
)

@Composable
fun KanbanBoard(
    viewModel: KanbanViewModel,
    modifier: Modifier = Modifier
) {
    var dragInfo by remember { mutableStateOf<DragInfo?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.columns) { column ->
                KanbanColumn(
                    column = column,
                    dragInfo = dragInfo,
                    onDragStart = { taskId ->
                        dragInfo = DragInfo(taskId, column.id)
                    },
                    onDragEnd = {
                        dragInfo = null
                        dragOffset = Offset.Zero
                    },
                    onDrag = { offset ->
                        dragOffset += offset
                    },
                    onDrop = { targetColumnId ->
                        dragInfo?.let { info ->
                            if (info.sourceColumnId != targetColumnId) {
                                viewModel.moveTask(info.taskId, info.sourceColumnId, targetColumnId)
                            }
                        }
                        dragInfo = null
                        dragOffset = Offset.Zero
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
    onDragEnd: () -> Unit,
    onDrag: (Offset) -> Unit,
    onDrop: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    Card(
        modifier = modifier
            .fillMaxHeight(0.95f)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragEnd = {
                        onDrop(column.id)
                        onDragEnd()
                    },
                    onDrag = { change, offset ->
                        change.consume()
                        onDrag(offset)
                        // Auto-scroll cuando se arrastra cerca de los bordes
                        scope.launch {
                            when {
                                offset.y < 0 -> lazyListState.scrollBy(-10f)
                                offset.y > 0 -> lazyListState.scrollBy(10f)
                            }
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = column.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(column.tasks, key = { it.id }) { task ->
                    val isDragging = dragInfo?.taskId == task.id

                    TaskCard(
                        title = task.title,
                        description = task.description,
                        modifier = Modifier
                            .zIndex(if (isDragging) 1f else 0f)
                            .graphicsLayer {
                                if (isDragging) {
                                    alpha = 0.5f
                                }
                            }
                            .pointerInput(Unit) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = { onDragStart(task.id) },
                                    onDragEnd = { onDragEnd() },
                                    onDrag = { change, offset ->
                                        change.consume()
                                        onDrag(offset)
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
fun TaskCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
