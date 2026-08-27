package cl.duoc.exercises._30_kata_integrador
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
class E01_csv_parserTest {
    @Test fun `split basico`() = assertEquals(listOf("a","b","c"), "a,b,c".splitCsvLine())
    @Test fun `split con vacias`() = assertEquals(listOf("a","b"), "a,,b,".splitCsvLine())
    @Test fun `parsear ok`() {
        val csv = "nombre,edad\nAna,30\nBeto,25"
        val r = parsearCsv(csv)
        assertTrue(r is ResultadoCsv.Exito)
        r as ResultadoCsv.Exito
        assertEquals(2, r.rows.size)
        assertEquals(listOf("Ana", "30"), r.rows[0].cells)
    }
    @Test fun `header invalido`() {
        val r = parsearCsv("solo_una_columna\nAna")
        assertTrue(r is ResultadoCsv.Error)
        r as ResultadoCsv.Error
        assertEquals("Header inválido", r.mensaje)
    }
}
