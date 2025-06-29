package org.example.organizame.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.organizame.data.models.Column

@Composable
fun KanbanBoard(
    columns: List<Column>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(columns) { column ->
            KanbanColumn(
                column = column,
                modifier = Modifier.width(300.dp)
            )
        }
    }
}

@Composable
fun KanbanColumn(
    column: Column,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxHeight()
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
            // Aquí irán las tareas de la columna
        }
    }
}
