package cl.duoc.exercises._07_data_classes_sealed
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_sealed_classTest {
    @Test fun `exito`() = assertEquals("Exito(42)", describir(Resultado.Exito(42)))
    @Test fun `error`() = assertEquals("Error: oops", describir(Resultado.Error("oops")))
    @Test fun `pendiente`() = assertEquals("Pendiente", describir(Resultado.Pendiente))
}