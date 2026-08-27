package com.example.baseproject.ui.screens.exercises.e08_room

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Esta mini-app es conceptual: no se ejecuta porque no agregamos Room
    como dependencia. El objetivo es mostrar cómo se vería una pantalla
    con persistencia local usando Room + Flow + Repository.

    Si quieres ejecutarla, agrega en build.gradle.kts:
      implementation("androidx.room:room-runtime:2.6.1")
      implementation("androidx.room:room-ktx:2.6.1")
      ksp("androidx.room:room-compiler:2.6.1")
"""

private val SOLUCION = """
    @Entity data class Note(@PrimaryKey val id: Int, val texto: String)
    @Dao interface NoteDao {
        @Query("SELECT * FROM Note") fun all(): Flow<List<Note>>
        @Insert suspend fun insert(note: Note)
    }
    @Database(entities = [Note::class], version = 1)
    abstract class AppDb : RoomDatabase() { abstract fun notes(): NoteDao }
    class NoteRepository(private val dao: NoteDao) {
        val notes: Flow<List<Note>> = dao.all()
        suspend fun add(texto: String) = dao.insert(Note(0, texto))
    }
    class NoteViewModel(private val repo: NoteRepository) : ViewModel() {
        val notes: StateFlow<List<Note>> = repo.notes.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
        fun add(texto: String) { viewModelScope.launch { repo.add(texto) } }
    }
""".trimIndent()

@Composable
fun RoomExerciseScreen() {
    MiniAppScaffold(
        titulo = "8. Room (preview)",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Esta pantalla no se ejecuta: es solo código de referencia.",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error)
                Text("Solución:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
