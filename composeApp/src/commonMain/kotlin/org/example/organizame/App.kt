package org.example.organizame

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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

        // Inicializar las columnas con algunas tareas de ejemplo
        remember {
            val columns = listOf(
                Column(
                    id = 1L,
                    boardId = 1L,
                    name = "Por hacer",
                    position = 0,
                    tasks = listOf(
                        Task(
                            id = 1L,
                            columnId = 1L,
                            title = "Tarea 1",
                            description = "Descripción de la tarea 1",
                            position = 0
                        ),
                        Task(
                            id = 2L,
                            columnId = 1L,
                            title = "Tarea 2",
                            description = "Descripción de la tarea 2",
                            position = 1
                        )
                    )
                ),
                Column(
                    id = 2L,
                    boardId = 1L,
                    name = "En progreso",
                    position = 1,
                    tasks = listOf(
                        Task(
                            id = 3L,
                            columnId = 2L,
                            title = "Tarea 3",
                            description = "Descripción de la tarea 3",
                            position = 0
                        )
                    )
                ),
                Column(
                    id = 3L,
                    boardId = 1L,
                    name = "Terminado",
                    position = 2,
                    tasks = emptyList()
                )
            )
            viewModel.setColumns(columns)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("OrganizaME") }
                )
            }
        ) { paddingValues ->
            KanbanBoard(
                viewModel = viewModel,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}