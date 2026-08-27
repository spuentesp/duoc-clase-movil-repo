package cl.duoc.exercises._01_tipos_y_strings
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
class E02_raw_stringsTest {
    @Test fun `sql insert`() {
        val sql = crearSqlInsert("usuarios", "nombre", "Ana", "edad", "30")
        assertTrue(sql.contains("INSERT INTO usuarios"))
        assertTrue(sql.contains("nombre = 'Ana'"))
        assertTrue(sql.contains("edad = '30'"))
    }
}
