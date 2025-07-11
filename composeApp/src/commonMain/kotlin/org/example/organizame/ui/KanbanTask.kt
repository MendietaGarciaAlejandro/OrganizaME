package org.example.organizame.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.organizame.data.models.Task

@Composable
fun KanbanTask(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )
            if (task.description?.isNotBlank() == true) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
            }
        }
    }
}
