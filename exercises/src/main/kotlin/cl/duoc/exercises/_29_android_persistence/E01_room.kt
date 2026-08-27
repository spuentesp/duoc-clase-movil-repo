package cl.duoc.exercises._29_android_persistence

// ============================================================
// BRIDGE: Room + Flow + Repository (solo lectura)
// ============================================================

/*
@Entity
data class Note(@PrimaryKey(autoGenerate = true) val id: Int = 0, val texto: String)

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note") fun all(): Flow<List<Note>>
    @Insert suspend fun insert(note: Note)
}

@Database(entities = [Note::class], version = 1)
abstract class AppDb : RoomDatabase() { abstract fun notes(): NoteDao }

class NoteRepository(private val dao: NoteDao) {
    val notes: Flow<List<Note>> = dao.all()
    suspend fun add(texto: String) = dao.insert(Note(texto = texto))
}
*/
