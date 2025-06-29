package org.example.organizame

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.organizame.data.models.Column
import org.example.organizame.ui.KanbanBoard
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("OrganizaME") }
                )
            }
        ) { paddingValues ->
            // Columnas de ejemplo
            val columns = listOf(
                Column(1, 1, "Por hacer", 0),
                Column(2, 1, "En progreso", 1),
                Column(3, 1, "Terminado", 2)
            )

            KanbanBoard(
                columns = columns,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}