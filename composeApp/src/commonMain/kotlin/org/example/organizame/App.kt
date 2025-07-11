package org.example.organizame

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.example.organizame.data.models.Column
import org.example.organizame.data.models.Task
import org.example.organizame.ui.KanbanBoard
import org.example.organizame.ui.KanbanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember { KanbanViewModel() }

        // Inicializo columnas de ejemplo solo una vez
        LaunchedEffect(Unit) {
            viewModel.initializeColumns(
                listOf(
                    Column(
                        id = 1L, boardId = 1L, name = "Por hacer", position = 0,
                        tasks = listOf(
                            Task(1L, 1L, "Tarea 1", "Detalle 1", 0),
                            Task(2L, 1L, "Tarea 2", "Detalle 2", 1)
                        )
                    ),
                    Column(
                        id = 2L, boardId = 1L, name = "En progreso", position = 1,
                        tasks = listOf(
                            Task(3L, 2L, "Tarea 3", "Detalle 3", 0)
                        )
                    ),
                    Column(id = 3L, boardId = 1L, name = "Terminado", position = 2)
                )
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("OrganizaME") })
            }
        ) { padding ->
            KanbanBoard(
                viewModel = viewModel,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}